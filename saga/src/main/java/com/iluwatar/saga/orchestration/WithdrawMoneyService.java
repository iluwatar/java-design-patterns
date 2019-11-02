package com.iluwatar.saga.orchestration;

public class WithdrawMoneyService extends Service<String> {
    @Override
    public String getName() {
        return "withdrawing Money";
    }

    @Override
    public ChapterResult<String> process(String value) {
        if(value.equals("no-money")){
            return ChapterResult.failure(value);
        }
        return super.process(value);
    }
}
