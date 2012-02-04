package net.sourceforge.reb4j.scala.charclass

sealed class PredefinedClass private[charclass] (val nameChar : Char) 
	extends CharClass
	with SelfContained
	with Union.Subset
	with Intersection.Superset
{
	protected def invertedNameChar = 
		if (nameChar.isUpper) 
			nameChar.toLower
		else
			nameChar.toUpper
	override def ^ = new PredefinedClass(invertedNameChar)
	override def unitableForm = "\\" + nameChar
}


final class NamedPredefinedClass private[charclass] (
		nameChar : Char, 
		val className : String
	) extends PredefinedClass(nameChar)
{
	def this (className : String) = this('p', className)
	override def ^ = new NamedPredefinedClass(invertedNameChar, className)
	override def unitableForm = super.unitableForm() + "{" + className + "}"
}


