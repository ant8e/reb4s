package net.sourceforge.reb4j.scala

@SerialVersionUID(1L)
final class Sequence private[scala] (val components : List[Sequence.Sequenceable]) 
	extends Expression 
	with Alternation.Alternative
	with Sequence.Ops
{
	type Sequenceable = Sequence.Sequenceable
	lazy val expression = (components addString new StringBuilder).toString
	
	override def + (right : Sequenceable) : Sequence = new Sequence(components :+ right)
	override def + (right : Sequence) = 
		new Sequence(components ++ right.components)
	
	override def equals (other : Any) = other match
	{
		case that : Sequence => this.components == that.components
		case _ => false
	}
	override lazy val hashCode = 31 * components.hashCode
}



object Sequence
{
	trait Ops
	{
		def + (right : Sequenceable) : Sequence
		def + (right : Sequence) : Sequence
		final def then (right : Sequenceable) = this + right
		final def then (right : Sequence) = this + right
	}
	
	trait Sequenceable extends Expression with Ops
	{
		final override def + (right : Sequenceable) = new Sequence(List(this, right))
		final override def + (right : Sequence) = new Sequence(this::right.components)
	}
}