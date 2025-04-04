
import jakarta.persistence.*

@Entity(name="users")
class User (
        var userId: String,
        var password: String,
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT 적용
        var id: Long? = null
)
