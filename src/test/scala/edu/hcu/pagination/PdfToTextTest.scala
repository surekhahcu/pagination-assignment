package edu.hcu.pagination

import org.scalatest.FunSuite

class PdfToTextTest extends FunSuite {


  test("content of pdfs ") {
    val content = PdfToText.getContent("/home/surekha/Documents/pdfs/pdf2.pdf")
    val expectedContent = "Now try to search using the regex « reg(ular expressions?|ex(p|es)?) » . This regex will find all\nnames, singular and plural, I have used on this page to say “regex”. If we only had plain text search,\nwe would\nhave needed 5 searches. With regexes, we need just one search. Regexes save you time when using \na tool like\nEditPad Pro. Select Count Matches in the Search menu to see how many times this regular \nexpression can\nmatch the file you have open in EditPad Pro"
    assert(content == expectedContent)
  }
}
