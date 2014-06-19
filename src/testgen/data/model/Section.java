package testgen.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Section {
	private String name;
	private List<Question> questions;

	public Section(String name) {
		this.name = name;
		this.questions = new ArrayList<>();
	}

	public void appendQuestion(Question question) {
		this.questions.add(question);
	}

	public void reduceNOfQuestionsTo(int n) {
		if (n < 0) {
			return;
		}

		if (n > questions.size()) {
			throw new IllegalArgumentException("There is only " + questions.size() + " questions in section " + name + ", but " + n + " was requested.");
		}
		Collections.shuffle(questions);
		while (questions.size() > n) {
			questions.remove(questions.size() - 1);
		}
	}

	public void shuffleAnswers() {
		for(Question question : questions) {
			question.shuffleAnswers();
		}
	}

	// autogenerated code
	public String getName() {
		return name;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questions == null) ? 0 : questions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Section other = (Section) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n\tSection [name=").append(name).append(", questions=").append(questions).append("]");
		return builder.toString();
	}

}