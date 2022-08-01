package me.wikmor.lynxnative.prompt;

import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.NumericPrompt;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.ValidatingPrompt;

public class NamePrompt implements Prompt {

	@Override
	public String getPromptText(ConversationContext context) {
		return "Please enter your name.";
	}

	@Override
	public boolean blocksForInput(ConversationContext context) {
		return true;
	}

	@Override
	public Prompt acceptInput(ConversationContext context, String input) {

		context.getForWhom().sendRawMessage("Your name has been set to: '" + input + "'");
		context.setSessionData(BoardingQuestion.NAME, input);

		return new AgePrompt();
	}

	class AgePrompt extends NumericPrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return "Please enter your age.";
		}

		@Override
		protected String getInputNotNumericText(ConversationContext context, String invalidInput) {
			return "Please enter a valid number! '" + invalidInput + "' is not a valid number!";
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, Number invalidInput) {
			int age = invalidInput.intValue();

			if (age < 15)
				return "Only players 15 years old or older are allowed to play on this server";

			return "Your age does not appear to be a valid number";
		}

		@Override
		protected boolean isNumberValid(ConversationContext context, Number input) {
			return input.intValue() > 14 && input.intValue() < 70;
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, Number input) {

			context.getForWhom().sendRawMessage("Your age has been set to: '" + input + "'");
			context.setSessionData(BoardingQuestion.AGE, input.intValue());

			return new SignUpReasonPrompt();
		}
	}

	class SignUpReasonPrompt extends ValidatingPrompt {

		@Override
		public String getPromptText(ConversationContext context) {
			return "What kind of servers do you like to play on? What brings you here?";
		}

		@Override
		protected boolean isInputValid(ConversationContext context, String input) {
			return input.contains(" ") && input.length() > 10;
		}

		@Override
		protected String getFailedValidationText(ConversationContext context, String invalidInput) {
			return "Please enter a full sentence as an answer.";
		}

		@Override
		protected Prompt acceptValidatedInput(ConversationContext context, String input) {

			context.setSessionData(BoardingQuestion.SIGNUP_REASON, input);

			return END_OF_CONVERSATION;
		}
	}

	public enum BoardingQuestion {
		NAME,
		AGE,
		SIGNUP_REASON;
	}
}
