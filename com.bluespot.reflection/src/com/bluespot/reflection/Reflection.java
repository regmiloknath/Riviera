package com.bluespot.reflection;

import com.bluespot.reflection.CallStack.Frame;


public final class Reflection {
    
    private Reflection() {
        throw new UnsupportedOperationException("Instantiation not allowed");
    }
    
    public static Frame toCallStackFrame(StackTraceElement stackElement) {
        return new Frame(stackElement.getClassName(), stackElement.getMethodName());
    }

    public static CallStack asCallStack(StackTraceElement[] stackElements) {
        CallStack callStack = new CallStack();
        for(int i = stackElements.length - 1; i >= 0; i--)
            callStack.push(Reflection.toCallStackFrame(stackElements[i]));
        return callStack;
    }

    public static CallStack getCurrentCallStack() {
        return Reflection.asCallStack((new Throwable()).getStackTrace());
    }

    public static Frame getCurrentFrame() {
        StackTraceElement[] stackElements = (new Throwable()).getStackTrace();
        for(StackTraceElement stackElement : stackElements) {
            Frame frame = Reflection.toCallStackFrame(stackElement);
            if(!CallStack.isIgnored(frame))
                return frame;
        }
        return new Frame("", "");
    }
    
}
