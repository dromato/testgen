package testgen.data.assembler;

import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import testgen.data.model.Question;
import testgen.data.model.Section;
import testgen.data.model.Test;

public class TestAsembler {
	protected class SectionAssembler {
		protected class QuestionAssembler {
			private final static String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
			
			public Question assemblyQuestionFromNode(Node node) {
				Element element = (Element) node;
				String questionContent = element.getElementsByTagName("content").item(0).getTextContent();
				Question question = new Question(questionContent);
				NodeList answerNodesList = element.getElementsByTagName("answer");

				for (int i = 0; i < answerNodesList.getLength(); i++) {
					Node answerNode = answerNodesList.item(i);
					question.appendAnswer(answerNode.getTextContent());
					if (answerNode.hasAttributes()) {
						if (answerNode.getAttributes().getNamedItem("valid") != null) {
							if (answerNode.getAttributes().getNamedItem("valid").getFirstChild().getTextContent().equals("true")) {
								question.setCorrectAnswer(answerNode.getTextContent());
							}
						}
					}
				}

				return question;
			}

			public StringBuilder assemblyStringFromQuestion(Question question) {
				StringBuilder questionBuilder = new StringBuilder();
				questionBuilder.append("\t").append(question.getQuestion());
				int answerN = 0;
				for(String answer : question.getAnswers()) {
					questionBuilder.append("\n\t\t" + ALPHABET.charAt(answerN++) + ") " + answer);
				}
				return questionBuilder;
			}

			public Object assemblyKeyFromQuestion(Question question) {
				StringBuilder questionBuilder = new StringBuilder();
				questionBuilder.append("\t").append(question.getQuestion());
				int answerN = 0;
				for(String answer : question.getAnswers()) {
					if(!answer.equals(question.getCorrectAnswer())) {
						answerN++;
						continue;
					}
					questionBuilder.append("\n\t\t-> " + answer);
				}
				return questionBuilder;
			}
		}

		private QuestionAssembler questionAssembler;

		public SectionAssembler() {
			this.questionAssembler = new QuestionAssembler();
		}

		public Section assemblySectionFromNode(Node node) {
			Element element = (Element) node;
			String sectionName = element.getElementsByTagName("name").item(0).getFirstChild().getTextContent();
			Section section = new Section(sectionName);
			NodeList questionNodesList = element.getElementsByTagName("question");

			for (int i = 0; i < questionNodesList.getLength(); i++) {
				section.appendQuestion(questionAssembler.assemblyQuestionFromNode(questionNodesList.item(i)));
			}

			return section;
		}

		public StringBuilder assemblyStringFromSection(Section section) {
			StringBuilder sectionBuilder = new StringBuilder();
			sectionBuilder.append(section.getName());
			for (Question question : section.getQuestions()) {
				sectionBuilder.append("\n\t").append(questionAssembler.assemblyStringFromQuestion(question)).append("\n");
			}
			return sectionBuilder;
		}

		public Object assemblyKeyFromSection(Section section) {
			StringBuilder sectionBuilder = new StringBuilder();
			sectionBuilder.append(section.getName());
			for (Question question : section.getQuestions()) {
				sectionBuilder.append("\n\t").append(questionAssembler.assemblyKeyFromQuestion(question)).append("\n");
			}
			return sectionBuilder;
		}
	}

	private SectionAssembler sectionAssembler;

	public TestAsembler() {
		this.sectionAssembler = new SectionAssembler();
	}

	public Test assemblyFromXmlDocument(Document document) {
		String testName = document.getElementsByTagName("test-name").item(0).getFirstChild().getTextContent();
		NodeList secionNodesList = document.getElementsByTagName("section");
		Test test = new Test(testName);

		for (int i = 0; i < secionNodesList.getLength(); i++) {
			test.appendSection(sectionAssembler.assemblySectionFromNode(secionNodesList.item(i)));
		}

		return test;
	}

	public StringBuilder assemblyStringFromTest(Test test, CommandLine commandLine) {
		StringBuilder testBuilder = new StringBuilder();
		testBuilder.append(test.getName()).append("\n\n");

		boolean randomizeAnswers = commandLine.hasOption("r");
		List<Section> sections = null;
		if(commandLine.hasOption("s")) {
			int questionsPerSection = Integer.valueOf(commandLine.getOptionValue("s"));
			sections = test.getSectionsWithNOfQuestionsReduced(questionsPerSection);
		} else {
			sections = test.getSections();
		}
		
		for (Section section : sections) {
			if(randomizeAnswers) {
				section.shuffleAnswers();
			}
			testBuilder.append(sectionAssembler.assemblyStringFromSection(section)).append("\n\n");
		}

		return testBuilder;
	}
	
	public StringBuilder assemblyKeyFormTest(Test test) {
		StringBuilder testBuilder = new StringBuilder();
		testBuilder.append(test.getName()).append("\n\n");
		for (Section section : test.getSections()) {
			testBuilder.append(sectionAssembler.assemblyKeyFromSection(section)).append("\n\n");
		}

		return testBuilder;
	}
}