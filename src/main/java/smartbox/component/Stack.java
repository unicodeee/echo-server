package smartbox.component;

import smartbox.Component;

public class Stack extends Component implements IStack {

    private java.util.Stack<Double> stack;

    public Stack() {
        super();
        stack = new java.util.Stack<Double>();
    }

    @Override
    public void push(Double num) {
        stack.push(num);
    }

    @Override
    public void pop() {
        if (!stack.isEmpty()) {
            stack.pop();
        }
    }

    @Override
    public Double top() {
        if (stack.isEmpty()) {
            return 0.0; // Default value when stack is empty
        }
        return stack.peek();
    }

    @Override
    public void clear() {
        stack.clear();
    }

    @Override
    public Boolean isEmpty() {
        return stack.isEmpty();
    }
}