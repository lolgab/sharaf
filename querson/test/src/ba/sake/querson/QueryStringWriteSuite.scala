package ba.sake.querson

import java.util.UUID

class QueryStringWriteSuite extends munit.FunSuite {

  val uuid = UUID.fromString("ef42f9e9-79b9-45eb-a938-95ac75aedf87")

  val cfgSeqNoBrackets = Config(SeqWriteMode.NoBrackets, ObjWriteMode.Brackets)
  val cfgSeqEmptyBrackets = Config(SeqWriteMode.EmptyBrackets, ObjWriteMode.Brackets)
  val cfgSeqBrackets = Config(SeqWriteMode.Brackets, ObjWriteMode.Brackets)

  val cfgObjBrackets = Config(SeqWriteMode.NoBrackets, ObjWriteMode.Brackets)
  val cfgObjDots = Config(SeqWriteMode.NoBrackets, ObjWriteMode.Dots)

  test("toQueryString should write simple query parameters to string") {
    val res1 = QuerySimple("some text", 42, uuid).toQueryString()
    assertEquals(res1, s"str=some+text&uuid=$uuid&int=42")
  }

  test("toQueryString should write enum query parameters to string") {
    val res1 = QueryEnum(Color.Red).toQueryString()
    assertEquals(res1, "color=Red")
  }

  test("toQueryString should write seq query parameters to string") {
    val queryData = QuerySeq(Seq("x", "y", "z"))
    assertEquals(queryData.toQueryString(cfgSeqNoBrackets), "a=x&a=y&a=z")
    assertEquals(queryData.toQueryString(cfgSeqEmptyBrackets), "a[]=x&a[]=y&a[]=z")
    assertEquals(queryData.toQueryString(cfgSeqBrackets), "a[2]=z&a[0]=x&a[1]=y")
  }

  test("toQueryString should write object query parameters to string") {
    val queryData = QueryNested("what?", Page(5, 42))
    assertEquals(queryData.toQueryString(cfgObjBrackets), "search=what%3F&p[number]=5&p[size]=42")
    assertEquals(queryData.toQueryString(cfgObjDots), "search=what%3F&p.size=42&p.number=5")
  }

  test("toQueryString should write default query parameters to string") {
    val res1 = QueryDefaults(opt = None, seq = Seq.empty).toQueryString()
    assertEquals(res1, "q=default")
  }

}
