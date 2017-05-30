package dao
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Userdata.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Userdata
   *  @param email Database column email SqlType(varchar), PrimaryKey, Length(64,true)
   *  @param username Database column username SqlType(varchar), Length(32,true)
   *  @param age Database column age SqlType(int4), Default(None) */
  final case class UserdataRow(email: String, username: String, age: Option[Int] = None)

  /** GetResult implicit for fetching UserdataRow objects using plain SQL queries */
  implicit def GetResultUserdataRow(implicit e0: GR[String], e1: GR[Option[Int]]): GR[UserdataRow] = GR { prs =>
    import prs._
    UserdataRow.tupled((<<[String], <<[String], <<?[Int]))
  }

  /** Table description of table userdata. Objects of this class serve as prototypes for rows in queries. */
  class Userdata(_tableTag: Tag) extends profile.api.Table[UserdataRow](_tableTag, "userdata") {
    def * = (email, username, age) <> (UserdataRow.tupled, UserdataRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? =
      (Rep.Some(email), Rep.Some(username), age).shaped.<>({ r =>
        import r._; _1.map(_ => UserdataRow.tupled((_1.get, _2.get, _3)))
      }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column email SqlType(varchar), PrimaryKey, Length(64,true) */
    val email: Rep[String] = column[String]("email", O.PrimaryKey, O.Length(64, varying = true))

    /** Database column username SqlType(varchar), Length(32,true) */
    val username: Rep[String] = column[String]("username", O.Length(32, varying = true))

    /** Database column age SqlType(int4), Default(None) */
    val age: Rep[Option[Int]] = column[Option[Int]]("age", O.Default(None))
  }

  /** Collection-like TableQuery object for table Userdata */
  lazy val Userdata = new TableQuery(tag => new Userdata(tag))
}
