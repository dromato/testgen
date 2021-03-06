package testgen.data.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Question {
	private String question;
	private List<String> answers;
	private String correctAnswer;

	public Question(String question) {
		this.question = question;
		this.answers = new ArrayList<>();
	}

	public void appendAnswer(String answer) {
		this.answers.add(answer);
	}

	// autogenerated code
	public String getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}

	public String getQuestion() {
		return question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + ((correctAnswer == null) ? 0 : correctAnswer.hashCode());
		return result;
	}

	public void shuffleAnswers() {
		Collections.shuffle(answers);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (correctAnswer == null) {
			if (other.correctAnswer != null)
				return false;
		} else if (!correctAnswer.equals(other.correctAnswer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\n\t\tQuestion [question=").append(question).append(", answers=").append(answers).append(", correctAnswer=").append(correctAnswer).append("]");
		return builder.toString();
	}

}
