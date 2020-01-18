package com.norair.lab.calculator;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Calculator {


    public String evaluate(String statement) {

        if (statement == null || statement.isEmpty()) {
            return null;
        }
        statement = statement.replaceAll(" ", "");
        // Проверяем пунктуацию переданного выражения на наличие недопустимых символов вообще, а так же в начале и конце в частности
        // Проверяем наличие идущих подряд повторяющихся операторов и точек
        boolean hasWrongPunct = statement.matches("(.*[.+*/]{2,}.*)||(.*[-]{3,}.*)||(^[.+*/)].*)||(.*[.+*/(-])||(.*[^0-9.+*/)(-].*)");
        if (hasWrongPunct) {
            return null;
        }
        //Делим строку на отдельные элементы и проверяем совпадает ли число открывающих и закрывающих скобок
        String separators = "+-*/()";
        List<String> list = new ArrayList<>();
        StringTokenizer separate = new StringTokenizer(statement, separators, true);
        while (separate.hasMoreTokens()) {
            list.add(separate.nextToken());
        }
        int openParentheses = 0;
        int closeParentheses = 0;
        for (String x : list) {
            if (x.equals("(")) {
                openParentheses++;
            }
            if (x.equals(")")) {
                closeParentheses++;
            }
        }
        if (openParentheses != closeParentheses) {
            return null;
        }
        //Считаем результат
        double result;
        try {
            result = doMathWithBraces(list);
        } catch (ArithmeticException ex) {
            ex.printStackTrace();
            return null;
        }
        //Округляем результат до 4 знаков после запятой, избавляемся от лишних нулей
        //Если double не содержит дробной части, приводим к int
        result = new BigDecimal(result).setScale(4, RoundingMode.HALF_UP).doubleValue();
        if (result == (int) result) {
            return String.format("%d", (int) result);
        } else {
            return String.valueOf(result);
        }

    }

    private static double doMathWithBraces(List<String> list) {
        //Метод выполняет расчет, если в выражении нет скобок, в противном случае рекурсивно вызывается для выражения в скобках
        if (!list.contains("(")) {
            return doMath(list);
        }
        List<String> resultList = new ArrayList<>();
        int open = 0;
        int close = 0;
        int openind = -1;
        boolean brace = false;
        for (int i = 0; i < list.size(); i++) {
            String temp = list.get(i);
            if (temp.equals("(")) {
                if (open == 0) {
                    openind = i;
                    brace = true;
                }
                open++;
            } else if (temp.equals(")")) {
                close++;
                if (close == open) {
                    resultList.add(String.valueOf(doMathWithBraces(list.subList(openind + 1, i))));
                    open--;
                    close--;
                    brace = false;
                }
            } else if (!brace){
                resultList.add(temp);
            }
        }
        return doMath(resultList);
    }

    private static double doMath(List<String> list) {
        // Преобразуем переданный список с математическим выражением в постфиксную форму c использованием стека операторов
        Deque<String> operators = new ArrayDeque<>();
        List<String> postfix = new ArrayList<>();
        Deque<Double> numbers = new ArrayDeque<>();

        String high = "[*/]";
        String low = "[+-]";
        ListIterator<String> iter = list.listIterator();
        boolean negative = false;
        while (iter.hasNext()) {
            String s = iter.next();
            if (s.equals("-")) {
                iter.previous();
                if (!iter.hasPrevious()) {
                    negative = true;
                    iter.next();
                    continue;
                }
                String prev = iter.previous();
                if (prev.matches(high) || prev.matches(low)) {
                    negative = true;
                    iter.next();
                    iter.next();
                    continue;
                }
                iter.next();
                iter.next();
            }

            if (s.matches(high) || s.matches(low)) {
                if (operators.isEmpty()) {
                    operators.push(s);
                    continue;
                }
                if (s.matches(high)) {
                    if (operators.getLast().matches(low)) {
                        operators.push(s);
                    } else {
                        postfix.add(operators.pop());
                        operators.push(s);
                    }
                } else if (s.matches(low)) {
                    if (operators.getLast().matches(low)) {
                        postfix.add(operators.pop());
                        operators.push(s);
                    } else {
                        postfix.add(operators.pop());
                        operators.push(s);
                    }
                }
            } else {
                if (negative) {
                    postfix.add("-" + s);
                    negative = false;
                } else {
                    postfix.add(s);
                }
            }
        }
        while (!operators.isEmpty()) {
            postfix.add(operators.pop());
        }
        // Вычисляем окончательный результат используя постфиксную форму и стек с числами
        for (String s : postfix) {
            switch (s) {
                case "/": {
                    double y = numbers.pop();
                    if (y == 0) {
                        throw new ArithmeticException("Devision by zero");
                    }
                    double x = numbers.pop();
                    numbers.push(x / y);
                    break;
                }
                case "-": {
                    double y = numbers.pop();
                    double x = numbers.pop();
                    numbers.push(x - y);
                    break;
                }
                case "*":
                    numbers.push(numbers.pop() * numbers.pop());

                    break;
                case "+":
                    numbers.push(numbers.pop() + numbers.pop());

                    break;
                default:
                    numbers.push(Double.parseDouble(s));

                    break;
            }
        }
        return numbers.pop();
    }
}
