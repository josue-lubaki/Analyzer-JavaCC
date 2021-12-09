package utility;

import java.util.Stack;

import pkgAttribute.IAttribute;
import pkgAttribute.JavaAttribute;
import pkgClass.IClass;
import pkgClass.JavaClass;
import pkgInterface.IInterface;
import pkgInterface.JavaInterface;
import pkgMethod.IMethod;
import pkgMethod.JavaMethod;
import pkgMethodCall.IMethodCall;
import pkgMethodCall.JavaMethodCall;

public class JavaObjetManager extends Stack<Object> {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

	public void addToCurrentObject(JavaAttribute attribute) {
        if (peek() instanceof IAttribute) {
            ((IAttribute) peek()).addAttribute(attribute);
        }
    }

    public void addToCurrentObject(JavaMethod javaMethod) {
        if (peek() instanceof IMethod) {
            ((IMethod) peek()).addMethod(javaMethod);
        }
        push(javaMethod);
    }

    public void addToCurrentObject(JavaClass javaClass) {
        if (peek() instanceof IClass) {
            ((IClass) peek()).addClass(javaClass);
        }
        push(javaClass);
    }

    public void addToCurrentObject(JavaMethodCall methodCall) {
        if (peek() instanceof IMethodCall) {
            ((IMethodCall) peek()).addMethodCall(methodCall);
        }
    }

    public void addToCurrentObject(JavaInterface javaInterface) {
        if (peek() instanceof IInterface) {
            ((IInterface) peek()).addInterface(javaInterface);
        }
        push(javaInterface);
    }

}
