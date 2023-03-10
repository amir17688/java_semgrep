package testcode.script;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.context.FacesContext;

public class ElExpressionSample {

    // ruleid: el-injection
    public void unsafeEL(String expression) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
        ELContext elContext = context.getELContext();
        ValueExpression vex = expressionFactory.createValueExpression(elContext, expression, String.class);
        String result = (String) vex.getValue(elContext);
        System.out.println(result);
    }

    // ok: el-injection
    public void safeEL() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
        ELContext elContext = context.getELContext();
        ValueExpression vex = expressionFactory.createValueExpression(elContext, "1+1", String.class);
        String result = (String) vex.getValue(elContext);
        System.out.println(result);
    }

    // ruleid: el-injection
    public void unsafeELMethod(ELContext elContext,ExpressionFactory expressionFactory, String expression) {
        expressionFactory.createMethodExpression(elContext, expression, String.class, new Class[]{Integer.class});
    }

    //ok: el-injection
    public void safeELMethod(ELContext elContext,ExpressionFactory expressionFactory) {
        expressionFactory.createMethodExpression(elContext, "1+1", String.class,new Class[] {Integer.class});
    }

    //ruleid: el-injection
    private void unsafeELTemplate(String message, ConstraintValidatorContext context) {
         context.disableDefaultConstraintViolation();
         context
             .someMethod()
             .buildConstraintViolationWithTemplate(message)
             .addConstraintViolation();
    }

    //ok: el-injection
    private void safeELTemplate(String message, ConstraintValidatorContext context) {
         context.disableDefaultConstraintViolation();
         context
             .someMethod()
             .buildConstraintViolationWithTemplate("somestring")
             .addConstraintViolation();
    }
}
