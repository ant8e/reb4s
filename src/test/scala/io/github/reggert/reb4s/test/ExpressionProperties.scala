package io.github.reggert.reb4s.test

import java.util.regex.PatternSyntaxException

import org.scalacheck.Gen
import org.scalacheck.Prop.forAll

import io.github.reggert.reb4s.Expression

trait ExpressionProperties[E <: Expression] {
	def toPattern(g : Gen[E]) = forAll(g) {e : E =>
		try {e.toPattern; true} catch {case e : PatternSyntaxException => false}
	}
}