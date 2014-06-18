package testgen.data.assembler;

import org.apache.commons.cli.CommandLine;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import testgen.data.model.Question;
import testgen.data.model.Section;
import testgen.data.model.Test;

import com.tutego.jrtf.Rtf;

import static com.tutego.jrtf.Rtf.rtf;
import static com.tutego.jrtf.RtfDocfmt.*;
import static com.tutego.jrtf.RtfHeader.*;
import static com.tutego.jrtf.RtfInfo.*;
import static com.tutego.jrtf.RtfFields.*;
import static com.tutego.jrtf.RtfPara.*;
import static com.tutego.jrtf.RtfSectionFormatAndHeaderFooter.*;
import static com.tutego.jrtf.RtfText.*;
import static com.tutego.jrtf.RtfUnit.*;

public class TestAssembler {
	protected class SectionAssembler {
		protected class QuestionAssembler {
			public Question assemblyQuestionFromNode(Node node) {
				Element element = (Element) node;
				String questionContent = element
						.getElementsByTagName("content").item(0)
						.getTextContent();
				Question question = new Question(questionContent);
				NodeList answerNodesList = element
						.getElementsByTagName("answer");

				for (int i = 0; i < answerNodesList.getLength(); i++) {
					Node answerNode = answerNodesList.item(i);
					question.appendAnswer(answerNode.getTextContent());
					if (answerNode.hasAttributes()) {
						if (answerNode.getAttributes().getNamedItem("valid") != null) {
							if (answerNode.getAttributes()
									.getNamedItem("valid").getFirstChild()
									.getTextContent().equals("true")) {
								question.setCorrectAnswer(answerNode
										.getTextContent());
							}
						}
					}
				}

				return question;
			}

			public Rtf assemblyRtfFromQuestion(Question question) {
				// TODO Auto-generated method stub
				return null;
			}
		}

		private QuestionAssembler questionAssembler;

		public SectionAssembler() {
			this.questionAssembler = new QuestionAssembler();
		}

		public Section assemblySectionFromNode(Node node) {
			Element element = (Element) node;
			String sectionName = element.getElementsByTagName("name").item(0)
					.getFirstChild().getTextContent();
			Section section = new Section(sectionName);
			NodeList questionNodesList = element
					.getElementsByTagName("question");

			for (int i = 0; i < questionNodesList.getLength(); i++) {
				section.appendQuestion(questionAssembler
						.assemblyQuestionFromNode(questionNodesList.item(i)));
			}

			return section;
		}

		public Rtf assemblyRtfFromSection(Section section) {
			Rtf sectionRtf = rtf().section(
					p(font(3,bold(section.getName()))).alignCentered()
							);
			
			for(Question question : section.getQuestions()) {
				sectionRtf.p(questionAssembler.assemblyRtfFromQuestion(question));
			}
			return sectionRtf;
		}
	}

	private SectionAssembler sectionAssembler;

	public TestAssembler() {
		this.sectionAssembler = new SectionAssembler();
	}

	public Test assemblyFromXmlDocument(Document document) {
		String testName = document.getElementsByTagName("test-name").item(0)
				.getFirstChild().getTextContent();
		NodeList secionNodesList = document.getElementsByTagName("section");
		Test test = new Test(testName);

		for (int i = 0; i < secionNodesList.getLength(); i++) {
			test.appendSection(sectionAssembler
					.assemblySectionFromNode(secionNodesList.item(i)));
		}

		return test;
	}

	public Rtf assemblyRtfFromTest(Test test, CommandLine commandLine) {
		int size = commandLine.hasOption("s") ? Integer.valueOf(commandLine.getOptionValue("s")) : -1;
		int nOfGroups = commandLine.hasOption("g") ? Integer.valueOf(commandLine.getOptionValue("g")) : 1;
		boolean equalDistribution = commandLine.hasOption("e");
		boolean randomizeAnswers = commandLine.hasOption("r");
		
		Rtf testRtf = rtf().section(p(font(4, test.getName())).alignCentered());
		for(Section section : test.getSections()) {
			testRtf.p(sectionAssembler.assemblyRtfFromSection(section));
		}
		
		return testRtf;
	}
}