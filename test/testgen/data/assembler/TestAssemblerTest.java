package testgen.data.assembler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import testgen.data.model.Question;
import testgen.data.model.Section;

public class TestAssemblerTest {
	private static TestAssembler assembler;
	private static Document inputXml;
	private static testgen.data.model.Test expectedTest;
	
	@BeforeClass
	public static void setUp() throws ParserConfigurationException {
		assembler = new TestAssembler();
		inputXml = buildXml();
		expectedTest = new testgen.data.model.Test("test-name");
		Question question = new Question("content");
		question.appendAnswer("answer");
		Section section = new Section("name");
		section.appendQuestion(question);
		expectedTest.appendSection(section);
	}

	private static Document buildXml() throws ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		Element testNode = document.createElement("test");
		Element testNameNode = document.createElement("test-name");
		testNameNode.setTextContent("test-name");
		Element sectionNode = document.createElement("section");
		Element nameNode = document.createElement("name");
		nameNode.setTextContent("name");
		Element questionNode = document.createElement("question");
		Element contentNode = document.createElement("content");
		contentNode.setTextContent("content");
		Element answerNode = document.createElement("answer");
		answerNode.setTextContent("answer");
		
		questionNode.appendChild(contentNode);
		questionNode.appendChild(answerNode);
		sectionNode.appendChild(nameNode);
		sectionNode.appendChild(questionNode);
		testNode.appendChild(sectionNode);
		testNode.appendChild(testNameNode);
		document.appendChild(testNode);
		
		return document;
	}
	
	@Test
	public void testAssemblyTest() {
		testgen.data.model.Test test = assembler.assemblyFromXmlDocument(inputXml);
		Assert.assertEquals(expectedTest, test);
	}
}
