package com.intellij.generatetestcases;


import com.intellij.generatetestcases.impl.TestClassImpl;
import com.intellij.generatetestcases.impl.TestMethodImpl;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.javadoc.PsiDocTag;
import com.intellij.generatetestcases.test.BaseTests;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.*;

public class TestMethodTest extends BaseTests {

    /**
     * @verifies return a description without leading or trailing spaces
     * @see TestMethod#getDescription()
     */
    @Test
    public void testGetDescription_shouldReturnADescriptionWithoutLeadingOrTrailingSpaces()
            throws Exception {

        Project project = getProject();
        //  create or get a Test Method
        PsiClass psiClass = createSutClass(project);
        PsiMethod[] methods = psiClass.findMethodsByName("getUserByUuid", false);

        //  create or get a tag
        PsiDocTag tag = methods[0].getDocComment().getTags()[3];

        TestMethod tm1 = new TestMethodImpl(tag, new TestClassImpl(psiClass));
        TestMethod tm = tm1;
        String description = tm.getDescription();

        //  verificar que la descripcion de retorno del metodo
        assertThat(description, is("fetch user with given uuid"));

    }

    /**
     * @verifies return the sut class for this method
     * @see TestMethod#getSutMethod()
     */
    @Test
    public void getSutMethod_shouldReturnTheSutClassForThisMethod()
            throws Exception {

    }

    /**
     * @verifies return the sut method for this method
     * @see TestMethod#getSutMethod()
     */
    @Test
    public void testGetSutMethod_shouldReturnTheSutMethodForThisMethod()
            throws Exception {

        //  instantiate test method
        Project project = getProject();
        //  create or get a Test Method
        PsiClass psiClass = createSutClass(project);
        PsiMethod[] methods = psiClass.findMethodsByName("getUserByUuid", false);

        //  create or get a tag
        PsiDocTag tag = methods[0].getDocComment().getTags()[3];

        TestMethod tm1 = new TestMethodImpl(tag, new TestClassImpl(psiClass));
        TestMethod tm = tm1;
        assertThat(tm.getSutMethod().getName(), is("getUserByUuid"));


    }

    /**
     * @verifies return a valid psiMethod if reallyExists returns true, false otherwise
     * @see TestMethod#getBackingMethod()
     */
    @Test
    public void testgetBackingMethod_shouldReturnAValidPsiMethodIfReallyExistsReturnsTrueFalseOtherwise()
            throws Exception {


        PsiClass psiClass = createSutClass(myProject);
        createTestClassForSut(myProject);

        TestClassImpl testClass = new TestClassImpl(psiClass);
        List<TestMethod> methods = testClass.getAllMethods();

        //  create or get a test method that really exists
        //  assert that reallyExists for the method return true and that the call to getBackingMethod return the right method
        TestMethod tm = findTestMethodInCollection(methods, "fetch user with given uuid", "getUserByUuid");
        assertThat(tm.getBackingMethod().getName(), is("getUserByUuid_shouldFetchUserWithGivenUuid"));
        assertThat(tm.reallyExists(), is(true));

        //  create or get a test method that doesn't exists
        tm = findTestMethodInCollection(methods, "find object given valid uuid", "getUserByUuid");

        //  assert that reallyExists for the method return false and that the call to getBackingMethod return false
        assertThat(tm.getBackingMethod(), nullValue());
        assertThat(tm.reallyExists(), is(false));
    }

    private TestMethod findTestMethodInCollection(List<TestMethod> methods, String shouldDescription, String sutMethodName) {
        TestMethod tm = null;
        for (TestMethod method : methods) {
            if (method.getDescription().equals(shouldDescription) && method.getSutMethod().getName().equals(sutMethodName)) {
                tm = method;
            }

        }
        return tm;
    }

    /**
     * @verifies return true only if this test method has a backing psiMethod
     * @see TestMethod#reallyExists()
     */
    @Test
    public void testreallyExists_shouldReturnTrueOnlyIfThisTestMethodHasABackingPsiMethod()
            throws Exception {

        PsiClass psiClass = createSutClass(myProject);
        createTestClassForSut(myProject);

        TestClassImpl testClass = new TestClassImpl(psiClass);
        List<TestMethod> methods = testClass.getAllMethods();

        //  create or get a test method that really exists
        //  assert that reallyExists for the method return true and that the call to getBackingMethod return the right method
        TestMethod tm = findTestMethodInCollection(methods, "fetch user with given uuid", "getUserByUuid");
        assertThat(tm.reallyExists(), is(true));

        tm = findTestMethodInCollection(methods, "find object given valid uuid", "getUserByUuid");
        assertThat(tm.reallyExists(), is(false));


    }

    /**
     * @verifies add required imports in test class as required for its javadoc and its body
     * @see TestMethod#create()
     */
    @Test
    public void testcreate_shouldAddRequiredImportsInTestClassAsRequiredForItsJavadocAndItsBody()
            throws Exception {

        // TODO create sut and test psi classes where test class doesn't have all required imports

        // TODO this is an oportunity to implement test framework strategy

        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies create a test method with the expected body and javadoc
     * @see TestMethod#create()
     */
    @Test
    public void testcreate_shouldCreateATestMethodWithTheExpectedBodyAndJavadoc()
            throws Exception {
        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }

    /**
     * @verifies create the current test method
     * @see TestMethod#create()
     */
    @Test
    public void testcreate_shouldCreateTheCurrentTestMethod() throws Exception {

        //  create a psi sut
        PsiClass psiClass = createSutClass(myProject);
        //  create a psi test class
        createTestClassForSut(myProject);

        //  create TestClass
        TestClass testClass = BDDCore.createTestClass(myProject, psiClass);

        //  get test method
        List<TestMethod> methods = testClass.getAllMethods();

        //  verify it is unitialized

        TestMethod tm = findTestMethodInCollection(methods, "find object given valid uuid", "getUserByUuid");
        assertThat(tm.reallyExists(), is(false));

        //  create it
        tm.create();

        //  verify it got initialized and its backing method exists

        assertThat(tm.reallyExists(), is(true));
        assertThat(tm.getBackingMethod(), is(not(nullValue())));

        //  assert the backing test method is the right one

        assertThat(tm.getBackingMethod().getName(), is("getUserByUuid_shouldFindObjectGivenValidUuid"));
    }

    /**
     * @verifies create the parent in the same content source root that its backing method if it didn't exist already
     * @see TestMethod#create()
     */
    @Test
    public void testcreate_shouldCreateTheParentInTheSameContentSourceRootThatItsBackingMethodIfItDidntExistAlready()
            throws Exception {
            //  create a psi sut without psi test class


        //  create TestClass


        //  get test method


        //  verify test method  is unitialized

        // TODO verify test class is unitialized and backingClass is null

        //  create it

        // get the parent test class backing psi class,

        // TODO assert location is the same that sut class


        //TODO auto-generated
        Assert.fail("Not yet implemented");
    }
}