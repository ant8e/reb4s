package io.github.reggert.reb4s.test

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Gen

import io.github.reggert.reb4s.{AnyChar, CompoundRaw, EscapedLiteral, InputBegin, InputEnd, InputEndSkipEOL, LineBegin, LineEnd, Literal, MatchEnd, NonwordBoundary, Quantifiable, Raw, WordBoundary}

trait RawGenerators extends LiteralGenerators {
	implicit val arbCompoundRaw = Arbitrary(genCompoundRaw)
	implicit val arbRaw = Arbitrary(genRaw)
	implicit val arbRawQuantifiable = Arbitrary(genRawQuantifiable)
	implicit val arbEscapedLiteral = Arbitrary(genEscapedLiteral)
	
	
	def genCompoundRaw : Gen[CompoundRaw] = for {
		first <- arbitrary[Raw]
		second <- arbitrary[Raw]
		otherComponents <- Gen.listOf(arbitrary[Raw])
	} yield CompoundRaw(first::second::otherComponents)
	
	def genRaw : Gen[Raw] = Gen.oneOf(
			arbitrary[EscapedLiteral], 
			arbitrary[Raw with Quantifiable], 
			arbitrary[CompoundRaw]
		)
	
	def genRawQuantifiable : Gen[Raw with Quantifiable] = Gen.oneOf(
			AnyChar,
			LineBegin,
			LineEnd,
			WordBoundary,
			NonwordBoundary,
			InputBegin,
			MatchEnd,
			InputEndSkipEOL,
			InputEnd
		)
		
	def genEscapedLiteral : Gen[EscapedLiteral] = arbitrary[Literal] map {EscapedLiteral}
}