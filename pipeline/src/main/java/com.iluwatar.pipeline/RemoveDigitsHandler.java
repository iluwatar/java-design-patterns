package com.iluwatar.pipeline;

class RemoveDigitsHandler implements Handler<String, String> {
    @Override
    public String process(String input) {
        StringBuilder inputWithoutDigits = new StringBuilder();

        for(int index=0; index<input.length(); index++) {
            char currentCharacter = input.charAt(index);
            if(Character.isDigit(currentCharacter)) {
                continue;
            }

            inputWithoutDigits.append(currentCharacter);
        }

        return inputWithoutDigits.toString();
    }
}