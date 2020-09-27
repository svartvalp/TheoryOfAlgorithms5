package com.svartvalp;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Factorial {


    public enum States {
        START(1), DIGITS(2), EXCLAMATION(3);

        private int value;

        States(int i) {
            this.value = i;
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        List<Character> digits = List.of('0', '1', '2', '3', '4', '5', '6', '7', '8', '9');
        Set<Integer> endStates = Set.of(States.EXCLAMATION.getValue());

        StateMachine machine = new StateMachine(States.START.getValue(), endStates);

        machine.add(States.START.getValue(),digits, States.DIGITS.getValue());
        machine.add(States.DIGITS.getValue(), digits, States.DIGITS.getValue());
        machine.add(States.DIGITS.getValue(), '!', States.EXCLAMATION.getValue());
        machine.add(States.EXCLAMATION.getValue(), '!', States.EXCLAMATION.getValue());
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        machine.findAll(input);
    }

}
