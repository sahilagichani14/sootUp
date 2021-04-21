package de.upb.swt.soot.test.java.bytecode.inputlocation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import categories.Java9Test;
import de.upb.swt.soot.core.inputlocation.AnalysisInputLocation;
import de.upb.swt.soot.java.bytecode.frontend.AsmJavaClassProvider;
import de.upb.swt.soot.java.bytecode.inputlocation.JrtFileSystemAnalysisInputLocation;
import de.upb.swt.soot.java.bytecode.inputlocation.ModuleFinder;
import de.upb.swt.soot.java.bytecode.inputlocation.PathBasedAnalysisInputLocation;
import de.upb.swt.soot.java.bytecode.interceptors.BytecodeBodyInterceptors;
import de.upb.swt.soot.java.core.JavaModuleIdentifierFactory;
import de.upb.swt.soot.java.core.JavaSootClass;
import de.upb.swt.soot.java.core.signatures.ModuleSignature;
import java.util.Collection;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/** @author Kaustubh Kelkar update on 16.04.2020 */
@Category(Java9Test.class)
public class ModuleFinderTest extends AnalysisInputLocationTest {

  @Test
  public void discoverModuleJavaBase() {
    ModuleFinder moduleFinder = new ModuleFinder(this.getClassProvider(), war.toString());
    AnalysisInputLocation<JavaSootClass> inputLocation =
        moduleFinder.discoverModule(JavaModuleIdentifierFactory.getModuleSignature("java.base"));
    assertTrue(inputLocation instanceof JrtFileSystemAnalysisInputLocation);
    assertTrue(
        inputLocation
            .getClassSource(getIdentifierFactory().getClassType("String", "java.lang"))
            .isPresent());
  }

  @Test
  public void discoverModuleByName() {
    ModuleFinder moduleFinder = new ModuleFinder(this.getClassProvider(), war.toString());
    AnalysisInputLocation<JavaSootClass> inputLocation =
        moduleFinder.discoverModule(JavaModuleIdentifierFactory.getModuleSignature("dummyWarApp"));
    assertTrue(inputLocation instanceof PathBasedAnalysisInputLocation);
  }

  @Test
  public void discoverModuleInAllModules() {
    ModuleFinder moduleFinder = new ModuleFinder(this.getClassProvider(), war.toString());
    Collection<ModuleSignature> modules = moduleFinder.discoverAllModules();
    String computedModuleName = "dummyWarApp";
    assertTrue(
        modules.contains(JavaModuleIdentifierFactory.getModuleSignature(computedModuleName)));
  }

  @Test
  public void testModuleJar() {
    ModuleFinder moduleFinder =
        new ModuleFinder(
            new AsmJavaClassProvider(BytecodeBodyInterceptors.Default.bodyInterceptors()),
            "../shared-test-resources/java9-target/de/upb/soot/namespaces/modules/");
    Collection<ModuleSignature> discoveredModules = moduleFinder.discoverAllModules();
    System.out.println(discoveredModules);

    assertTrue(
        discoveredModules.contains(JavaModuleIdentifierFactory.getModuleSignature("de.upb.mod")));
  }

  @Test
  public void testAutomaticModuleNaming() {
    assertEquals("foo.bar", ModuleFinder.createModuleNameForAutomaticModule("foo-bar.jar"));
    assertEquals("foo", ModuleFinder.createModuleNameForAutomaticModule("foo-1.2.3-SNAPSHOT.jar"));
  }
}
