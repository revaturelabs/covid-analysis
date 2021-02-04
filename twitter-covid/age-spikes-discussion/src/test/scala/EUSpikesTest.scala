import EUSpikes._
import org.scalatest.flatspec.AnyFlatSpec

class EUSpikesTest extends AnyFlatSpec {

  "Dataframe" should "not have age_groups 80+" in {
    val df = filterAgeGroups()
    assert(df.filter(df("age_group") === "80+yr").count() == 0)
  }
}
