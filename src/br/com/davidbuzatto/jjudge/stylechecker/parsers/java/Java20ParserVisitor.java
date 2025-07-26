// Generated from grammars/Java20Parser.g4 by ANTLR 4.13.2
package br.com.davidbuzatto.jjudge.stylechecker.parsers.java;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Java20Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Java20ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link Java20Parser#start_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStart_(Java20Parser.Start_Context ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#identifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdentifier(Java20Parser.IdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeIdentifier(Java20Parser.TypeIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unqualifiedMethodIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnqualifiedMethodIdentifier(Java20Parser.UnqualifiedMethodIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#contextualKeyword}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContextualKeyword(Java20Parser.ContextualKeywordContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#contextualKeywordMinusForTypeIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContextualKeywordMinusForTypeIdentifier(Java20Parser.ContextualKeywordMinusForTypeIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#contextualKeywordMinusForUnqualifiedMethodIdentifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContextualKeywordMinusForUnqualifiedMethodIdentifier(Java20Parser.ContextualKeywordMinusForUnqualifiedMethodIdentifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLiteral(Java20Parser.LiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#primitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimitiveType(Java20Parser.PrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#numericType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumericType(Java20Parser.NumericTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#integralType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIntegralType(Java20Parser.IntegralTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#floatingPointType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFloatingPointType(Java20Parser.FloatingPointTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#referenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReferenceType(Java20Parser.ReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#coit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCoit(Java20Parser.CoitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceType(Java20Parser.ClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassType(Java20Parser.ClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceType(Java20Parser.InterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeVariable(Java20Parser.TypeVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(Java20Parser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#dims}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDims(Java20Parser.DimsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameter(Java20Parser.TypeParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeParameterModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameterModifier(Java20Parser.TypeParameterModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeBound(Java20Parser.TypeBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#additionalBound}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditionalBound(Java20Parser.AdditionalBoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArguments(Java20Parser.TypeArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeArgumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentList(Java20Parser.TypeArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgument(Java20Parser.TypeArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#wildcard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcard(Java20Parser.WildcardContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#wildcardBounds}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcardBounds(Java20Parser.WildcardBoundsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#moduleName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleName(Java20Parser.ModuleNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#packageName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageName(Java20Parser.PackageNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeName(Java20Parser.TypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#packageOrTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageOrTypeName(Java20Parser.PackageOrTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#expressionName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionName(Java20Parser.ExpressionNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodName(Java20Parser.MethodNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#ambiguousName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAmbiguousName(Java20Parser.AmbiguousNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#compilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompilationUnit(Java20Parser.CompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#ordinaryCompilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrdinaryCompilationUnit(Java20Parser.OrdinaryCompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#modularCompilationUnit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModularCompilationUnit(Java20Parser.ModularCompilationUnitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#packageDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageDeclaration(Java20Parser.PackageDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#packageModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPackageModifier(Java20Parser.PackageModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#importDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportDeclaration(Java20Parser.ImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#singleTypeImportDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleTypeImportDeclaration(Java20Parser.SingleTypeImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeImportOnDemandDeclaration(Java20Parser.TypeImportOnDemandDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#singleStaticImportDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleStaticImportDeclaration(Java20Parser.SingleStaticImportDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#staticImportOnDemandDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticImportOnDemandDeclaration(Java20Parser.StaticImportOnDemandDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#topLevelClassOrInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTopLevelClassOrInterfaceDeclaration(Java20Parser.TopLevelClassOrInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#moduleDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleDeclaration(Java20Parser.ModuleDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#moduleDirective}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModuleDirective(Java20Parser.ModuleDirectiveContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#requiresModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRequiresModifier(Java20Parser.RequiresModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDeclaration(Java20Parser.ClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#normalClassDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalClassDeclaration(Java20Parser.NormalClassDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassModifier(Java20Parser.ClassModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameters(Java20Parser.TypeParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeParameterList(Java20Parser.TypeParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classExtends}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassExtends(Java20Parser.ClassExtendsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classImplements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassImplements(Java20Parser.ClassImplementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceTypeList(Java20Parser.InterfaceTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classPermits}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassPermits(Java20Parser.ClassPermitsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBody(Java20Parser.ClassBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassBodyDeclaration(Java20Parser.ClassBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassMemberDeclaration(Java20Parser.ClassMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#fieldDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldDeclaration(Java20Parser.FieldDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#fieldModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldModifier(Java20Parser.FieldModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableDeclaratorList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorList(Java20Parser.VariableDeclaratorListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(Java20Parser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableDeclaratorId}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclaratorId(Java20Parser.VariableDeclaratorIdContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializer(Java20Parser.VariableInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannType(Java20Parser.UnannTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannPrimitiveType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannPrimitiveType(Java20Parser.UnannPrimitiveTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannReferenceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannReferenceType(Java20Parser.UnannReferenceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannClassOrInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannClassOrInterfaceType(Java20Parser.UnannClassOrInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#uCOIT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUCOIT(Java20Parser.UCOITContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannClassType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannClassType(Java20Parser.UnannClassTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannInterfaceType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannInterfaceType(Java20Parser.UnannInterfaceTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannTypeVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannTypeVariable(Java20Parser.UnannTypeVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unannArrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnannArrayType(Java20Parser.UnannArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclaration(Java20Parser.MethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodModifier(Java20Parser.MethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodHeader(Java20Parser.MethodHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#result}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResult(Java20Parser.ResultContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodDeclarator(Java20Parser.MethodDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#receiverParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReceiverParameter(Java20Parser.ReceiverParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#formalParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameterList(Java20Parser.FormalParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#formalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFormalParameter(Java20Parser.FormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableArityParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableArityParameter(Java20Parser.VariableArityParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableModifier(Java20Parser.VariableModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#throwsT}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowsT(Java20Parser.ThrowsTContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#exceptionTypeList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionTypeList(Java20Parser.ExceptionTypeListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#exceptionType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionType(Java20Parser.ExceptionTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodBody(Java20Parser.MethodBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#instanceInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInstanceInitializer(Java20Parser.InstanceInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#staticInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStaticInitializer(Java20Parser.StaticInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclaration(Java20Parser.ConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constructorModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorModifier(Java20Parser.ConstructorModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constructorDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorDeclarator(Java20Parser.ConstructorDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#simpleTypeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleTypeName(Java20Parser.SimpleTypeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constructorBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstructorBody(Java20Parser.ConstructorBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#explicitConstructorInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExplicitConstructorInvocation(Java20Parser.ExplicitConstructorInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enumDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumDeclaration(Java20Parser.EnumDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enumBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBody(Java20Parser.EnumBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enumConstantList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstantList(Java20Parser.EnumConstantListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enumConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstant(Java20Parser.EnumConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enumConstantModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumConstantModifier(Java20Parser.EnumConstantModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enumBodyDeclarations}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnumBodyDeclarations(Java20Parser.EnumBodyDeclarationsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordDeclaration(Java20Parser.RecordDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordHeader}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordHeader(Java20Parser.RecordHeaderContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordComponentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponentList(Java20Parser.RecordComponentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordComponent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponent(Java20Parser.RecordComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableArityRecordComponent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableArityRecordComponent(Java20Parser.VariableArityRecordComponentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordComponentModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordComponentModifier(Java20Parser.RecordComponentModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordBody(Java20Parser.RecordBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#recordBodyDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRecordBodyDeclaration(Java20Parser.RecordBodyDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#compactConstructorDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompactConstructorDeclaration(Java20Parser.CompactConstructorDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceDeclaration(Java20Parser.InterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#normalInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalInterfaceDeclaration(Java20Parser.NormalInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceModifier(Java20Parser.InterfaceModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceExtends}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceExtends(Java20Parser.InterfaceExtendsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfacePermits}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfacePermits(Java20Parser.InterfacePermitsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceBody(Java20Parser.InterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMemberDeclaration(Java20Parser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constantDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantDeclaration(Java20Parser.ConstantDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constantModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantModifier(Java20Parser.ConstantModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceMethodDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodDeclaration(Java20Parser.InterfaceMethodDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#interfaceMethodModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInterfaceMethodModifier(Java20Parser.InterfaceMethodModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#annotationInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationInterfaceDeclaration(Java20Parser.AnnotationInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#annotationInterfaceBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationInterfaceBody(Java20Parser.AnnotationInterfaceBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#annotationInterfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationInterfaceMemberDeclaration(Java20Parser.AnnotationInterfaceMemberDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#annotationInterfaceElementDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationInterfaceElementDeclaration(Java20Parser.AnnotationInterfaceElementDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#annotationInterfaceElementModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotationInterfaceElementModifier(Java20Parser.AnnotationInterfaceElementModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#defaultValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefaultValue(Java20Parser.DefaultValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#annotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnnotation(Java20Parser.AnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#normalAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalAnnotation(Java20Parser.NormalAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#elementValuePairList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePairList(Java20Parser.ElementValuePairListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#elementValuePair}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValuePair(Java20Parser.ElementValuePairContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#elementValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValue(Java20Parser.ElementValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#elementValueArrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueArrayInitializer(Java20Parser.ElementValueArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#elementValueList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementValueList(Java20Parser.ElementValueListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#markerAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMarkerAnnotation(Java20Parser.MarkerAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#singleElementAnnotation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSingleElementAnnotation(Java20Parser.SingleElementAnnotationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#arrayInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayInitializer(Java20Parser.ArrayInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableInitializerList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableInitializerList(Java20Parser.VariableInitializerListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(Java20Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#blockStatements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatements(Java20Parser.BlockStatementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#blockStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockStatement(Java20Parser.BlockStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#localClassOrInterfaceDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalClassOrInterfaceDeclaration(Java20Parser.LocalClassOrInterfaceDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#localVariableDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclaration(Java20Parser.LocalVariableDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#localVariableType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableType(Java20Parser.LocalVariableTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#localVariableDeclarationStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalVariableDeclarationStatement(Java20Parser.LocalVariableDeclarationStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(Java20Parser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#statementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementNoShortIf(Java20Parser.StatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#statementWithoutTrailingSubstatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementWithoutTrailingSubstatement(Java20Parser.StatementWithoutTrailingSubstatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#emptyStatement_}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEmptyStatement_(Java20Parser.EmptyStatement_Context ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#labeledStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledStatement(Java20Parser.LabeledStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#labeledStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledStatementNoShortIf(Java20Parser.LabeledStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(Java20Parser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#statementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpression(Java20Parser.StatementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#ifThenStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenStatement(Java20Parser.IfThenStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#ifThenElseStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatement(Java20Parser.IfThenElseStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#ifThenElseStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfThenElseStatementNoShortIf(Java20Parser.IfThenElseStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#assertStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssertStatement(Java20Parser.AssertStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#switchStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchStatement(Java20Parser.SwitchStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#switchBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlock(Java20Parser.SwitchBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#switchRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchRule(Java20Parser.SwitchRuleContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#switchBlockStatementGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchBlockStatementGroup(Java20Parser.SwitchBlockStatementGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#switchLabel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchLabel(Java20Parser.SwitchLabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#caseConstant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCaseConstant(Java20Parser.CaseConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(Java20Parser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#whileStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatementNoShortIf(Java20Parser.WhileStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#doStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDoStatement(Java20Parser.DoStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(Java20Parser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#forStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatementNoShortIf(Java20Parser.ForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#basicForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicForStatement(Java20Parser.BasicForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#basicForStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBasicForStatementNoShortIf(Java20Parser.BasicForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#forInit}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForInit(Java20Parser.ForInitContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#forUpdate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForUpdate(Java20Parser.ForUpdateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#statementExpressionList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementExpressionList(Java20Parser.StatementExpressionListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enhancedForStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForStatement(Java20Parser.EnhancedForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#enhancedForStatementNoShortIf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEnhancedForStatementNoShortIf(Java20Parser.EnhancedForStatementNoShortIfContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(Java20Parser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(Java20Parser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(Java20Parser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#throwStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowStatement(Java20Parser.ThrowStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#synchronizedStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSynchronizedStatement(Java20Parser.SynchronizedStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#tryStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryStatement(Java20Parser.TryStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#catches}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatches(Java20Parser.CatchesContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#catchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchClause(Java20Parser.CatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#catchFormalParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchFormalParameter(Java20Parser.CatchFormalParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#catchType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCatchType(Java20Parser.CatchTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#finallyBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinallyBlock(Java20Parser.FinallyBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#tryWithResourcesStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTryWithResourcesStatement(Java20Parser.TryWithResourcesStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#resourceSpecification}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceSpecification(Java20Parser.ResourceSpecificationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#resourceList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResourceList(Java20Parser.ResourceListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#resource}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitResource(Java20Parser.ResourceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#variableAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAccess(Java20Parser.VariableAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#yieldStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYieldStatement(Java20Parser.YieldStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#pattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPattern(Java20Parser.PatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypePattern(Java20Parser.TypePatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(Java20Parser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(Java20Parser.PrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#primaryNoNewArray}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimaryNoNewArray(Java20Parser.PrimaryNoNewArrayContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#pNNA}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPNNA(Java20Parser.PNNAContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassLiteral(Java20Parser.ClassLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classInstanceCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassInstanceCreationExpression(Java20Parser.ClassInstanceCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unqualifiedClassInstanceCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnqualifiedClassInstanceCreationExpression(Java20Parser.UnqualifiedClassInstanceCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#classOrInterfaceTypeToInstantiate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassOrInterfaceTypeToInstantiate(Java20Parser.ClassOrInterfaceTypeToInstantiateContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#typeArgumentsOrDiamond}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTypeArgumentsOrDiamond(Java20Parser.TypeArgumentsOrDiamondContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#arrayCreationExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreationExpression(Java20Parser.ArrayCreationExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#arrayCreationExpressionWithoutInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreationExpressionWithoutInitializer(Java20Parser.ArrayCreationExpressionWithoutInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#arrayCreationExpressionWithInitializer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayCreationExpressionWithInitializer(Java20Parser.ArrayCreationExpressionWithInitializerContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#dimExprs}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimExprs(Java20Parser.DimExprsContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#dimExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDimExpr(Java20Parser.DimExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#arrayAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayAccess(Java20Parser.ArrayAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#fieldAccess}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFieldAccess(Java20Parser.FieldAccessContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodInvocation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodInvocation(Java20Parser.MethodInvocationContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#argumentList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgumentList(Java20Parser.ArgumentListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#methodReference}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMethodReference(Java20Parser.MethodReferenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#postfixExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpression(Java20Parser.PostfixExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#pfE}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfE(Java20Parser.PfEContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#postIncrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostIncrementExpression(Java20Parser.PostIncrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#postDecrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostDecrementExpression(Java20Parser.PostDecrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unaryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpression(Java20Parser.UnaryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#preIncrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreIncrementExpression(Java20Parser.PreIncrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#preDecrementExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPreDecrementExpression(Java20Parser.PreDecrementExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#unaryExpressionNotPlusMinus}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpressionNotPlusMinus(Java20Parser.UnaryExpressionNotPlusMinusContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#castExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCastExpression(Java20Parser.CastExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#multiplicativeExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpression(Java20Parser.MultiplicativeExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#additiveExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpression(Java20Parser.AdditiveExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#shiftExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitShiftExpression(Java20Parser.ShiftExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#relationalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRelationalExpression(Java20Parser.RelationalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#equalityExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpression(Java20Parser.EqualityExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#andExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpression(Java20Parser.AndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#exclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExclusiveOrExpression(Java20Parser.ExclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#inclusiveOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInclusiveOrExpression(Java20Parser.InclusiveOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#conditionalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalAndExpression(Java20Parser.ConditionalAndExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#conditionalOrExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalOrExpression(Java20Parser.ConditionalOrExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#conditionalExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConditionalExpression(Java20Parser.ConditionalExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#assignmentExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentExpression(Java20Parser.AssignmentExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(Java20Parser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#leftHandSide}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftHandSide(Java20Parser.LeftHandSideContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#assignmentOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentOperator(Java20Parser.AssignmentOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#lambdaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaExpression(Java20Parser.LambdaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#lambdaParameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameters(Java20Parser.LambdaParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#lambdaParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameterList(Java20Parser.LambdaParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#lambdaParameter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameter(Java20Parser.LambdaParameterContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#lambdaParameterType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaParameterType(Java20Parser.LambdaParameterTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#lambdaBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLambdaBody(Java20Parser.LambdaBodyContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#switchExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSwitchExpression(Java20Parser.SwitchExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link Java20Parser#constantExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstantExpression(Java20Parser.ConstantExpressionContext ctx);
}