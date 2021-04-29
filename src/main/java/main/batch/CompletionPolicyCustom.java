package main.batch;

import org.springframework.batch.repeat.CompletionPolicy;
import org.springframework.batch.repeat.RepeatContext;
import org.springframework.batch.repeat.RepeatStatus;

public class CompletionPolicyCustom implements CompletionPolicy {

	public boolean isComplete(RepeatContext context, RepeatStatus result) {
		if (result == RepeatStatus.FINISHED)
			return true;
		else
			return isComplete(context);
	}

	@Override
	public boolean isComplete(RepeatContext context) {
		return false;
	}

	@Override
	public RepeatContext start(RepeatContext parent) {
		return parent;
	}

	@Override
	public void update(RepeatContext context) {
	}
}
