import EUSpikes._
import org.scalatest.flatspec.AnyFlatSpec

class EUSpikesTest extends AnyFlatSpec {
//  val df = pullData()

  "Dataframe" should "not be empty" in {
    val df = pullData()
    assert(df.count() > 0)
  }

  "Dataframe" should "not have age_groups 80+" in {
    val df = filterAgeGroups()
    assert(df.filter(df("age_group") === "80+yr").count() == 0)
  }

  "Dataframe" should "have 2 columns" in {
    val df = groupData()
    assert(df.columns.size == 2)
  }

}
