package testgen.data.assembler;

import org.apache.commons.cli.CommandLine;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import testgen.data.model.Question;
import testgen.data.model.Section;
import testgen.data.model.Test;

import com.tutego.jrtf.Rtf;

public class TestAssembler {
	protected class SectionAssembler {
		public Section assemblySectionFromNode(Node node) {
			return null;
			
		}
	}

	protected class QuestionAssembler {
		public Question assemblyQuestionFromNode(Node node) {
			return null;
			
		}
	}

	private SectionAssembler sectionAssembler;
	private QuestionAssembler questionAssembler;
	
	public TestAssembler() {
		this.sectionAssembler = new SectionAssembler();
		this.questionAssembler = new QuestionAssembler();
	}

	public Test assemblyFromXmlDocument(Document document) {
		String testName = document.getElementsByTagName("test-name").item(0)
				.getChildNodes().item(0).getTextContent();
		NodeList secionNodesList = document.getElementsByTagName("section");
		Test test = new Test(testName);
		for (int i = 0; i < secionNodesList.getLength(); i++) {
			
		}
		return null;
	}

	public Rtf assemblyRtfFromTest(Test test, CommandLine commandLine) {
		throw new NotImplementedException();
	}
}