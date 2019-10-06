package com.iluwatar.pipeline;

class RemoveAlphabetsHandler implements Handler<String, String> {
    @Override
    public String process(String input) {
        StringBuilder inputWithoutAlphabets = new StringBuilder();

        for(int index=0; index<input.length(); index++) {
            char currentCharacter = input.charAt(index);
            if(Character.isAlphabetic(currentCharacter)) {
                continue;
            }

            inputWithoutAlphabets.append(currentCharacter);
        }

        return inputWithoutAlphabets.toString();
    }
}