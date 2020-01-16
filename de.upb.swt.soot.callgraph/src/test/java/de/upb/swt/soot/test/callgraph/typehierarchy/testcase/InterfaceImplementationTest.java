package de.upb.swt.soot.test.callgraph.typehierarchy.testcase;

import static org.junit.Assert.assertEquals;

import categories.Java8Test;
import de.upb.swt.soot.callgraph.typehierarchy.TypeHierarchy;
import de.upb.swt.soot.callgraph.typehierarchy.ViewTypeHierarchy;
import de.upb.swt.soot.core.types.ClassType;
import de.upb.swt.soot.test.callgraph.typehierarchy.JavaTypeHierarchyBase;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/** @author: Hasitha Rajapakse * */
@Category(Java8Test.class)
public class InterfaceImplementationTest extends JavaTypeHierarchyBase {
  @Test
  public void method() {
    ViewTypeHierarchy typeHierarchy =
        (ViewTypeHierarchy) TypeHierarchy.fromView(customTestWatcher.getView());
    Set<ClassType> interfaceSet = new HashSet<>();
    interfaceSet.add(getClassType("InterfaceA"));
    assertEquals(
        typeHierarchy.implementedInterfacesOf(getClassType("InterfaceImplementation")),
        interfaceSet);
    Set<ClassType> implementerSet = new HashSet<>();
    implementerSet.add(getClassType("InterfaceImplementation"));
    assertEquals(typeHierarchy.implementersOf(getClassType("InterfaceA")), implementerSet);
    assertEquals(typeHierarchy.subtypesOf(getClassType("InterfaceA")), implementerSet);
  }
}