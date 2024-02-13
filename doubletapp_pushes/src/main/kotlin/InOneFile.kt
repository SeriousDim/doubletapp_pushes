import java.io.PrintStream
import java.util.*
import kotlin.math.sqrt


/*
package: system.launcher
========================
 */

interface ILauncher {
    fun launch()
}

class SystemLauncher: ILauncher {
    override fun launch() {
        val inputReader = Scanner(System.`in`)
        val outputStream = System.out

        val view = ConsoleView(inputReader, outputStream)
        val viewProcessLauncher = ViewProcessLauncher(view)

        viewProcessLauncher.launch()
    }
}

class ViewProcessLauncher(
    val view: IViewProcess
): ILauncher {
    override fun launch() {
        view.onContextCreate()
        view.onStart()
    }
}

/*
package: system.model.context
=============================
 */

interface IContext {
    fun getGestureListenerService(): IGestureListenerService

    fun getPushNotificationService(): IPushNotificationService
}

open class SystemContext (
    val user: User = User(1, Gender.MALE),
    var time: Long = 0,
    var osVersion: Int = 1
): IContext {
    private val gestureListenerService = GestureListenerService()
    private val pushNotificationService = PushNotificationService()

    override fun getGestureListenerService(): IGestureListenerService {
        return this.gestureListenerService
    }

    override fun getPushNotificationService(): IPushNotificationService {
        return this.pushNotificationService
    }

}

/*
package: system.model.user
==========================
 */

enum class Gender {
    MALE, FEMALE
}

data class User(
    var age: Int,
    var gender: Gender
)

/*
package: system.services.gesture.model
======================================
 */

open class FingerGesture(
    var x: Float = 0f,
    var y: Float = 0f
);

/*
package: system.services.gesture
================================
 */

interface IGestureListenerService {
    fun setX(x: Float)

    fun setY(y: Float)

    fun getGesture(): FingerGesture
}

class GestureListenerService: IGestureListenerService {
    private val gesture = FingerGesture()

    override fun setX(x: Float) {
        this.gesture.x = x
    }

    override fun setY(y: Float) {
        this.gesture.y = y
    }

    override fun getGesture(): FingerGesture {
        return this.gesture
    }
}

/*
package: system.services.push.model.interfaces
===================================
 */

interface IPush {
    val context: SystemContext
    var text: String
    fun shouldBeFiltered(): Boolean;
}

interface IAgePush: IPush {
    var age: Int;

    override fun shouldBeFiltered(): Boolean {
        val user = context.user
        return this.age > user.age
    }
}

interface IExpiringPush: IPush {
    var expiryDate: Long;

    override fun shouldBeFiltered(): Boolean {
        return this.expiryDate < context.time
    }
}

interface IGenderPush: IPush {
    var gender: Gender;

    override fun shouldBeFiltered(): Boolean {
        val user = context.user;
        return user.gender != this.gender
    }
}

interface ILocationPush: IPush {
    var x: Float;
    var y: Float;
    var radius: Int;

    fun getDistanceToUserPosition(): Float {
        val gesture = context.getGestureListenerService().getGesture();
        return sqrt((gesture.x - this.x)*(gesture.x - this.x)
                + (gesture.y - this.y)*(gesture.y - this.y))
    }

    override fun shouldBeFiltered(): Boolean {
        return this.getDistanceToUserPosition() > this.radius
    }
}

interface ITechPush: IPush {
    var osVersion: Int;

    override fun shouldBeFiltered(): Boolean {
        return context.osVersion > this.osVersion
    }
}

/*
package: system.services.push.model.implementations
===================================
 */

data class AgeSpecificPush(
    override val context: SystemContext,
    override var text: String,
    override var expiryDate: Long,
    override var age: Int
): IExpiringPush, IAgePush {
    override fun shouldBeFiltered(): Boolean {
        return super<IExpiringPush>.shouldBeFiltered()
                || super<IAgePush>.shouldBeFiltered()
    }
}

data class GenderAgePush(
    override val context: SystemContext,
    override var text: String,
    override var gender: Gender,
    override var age: Int
): IAgePush, IGenderPush {
    override fun shouldBeFiltered(): Boolean {
        return super<IAgePush>.shouldBeFiltered()
                || super<IGenderPush>.shouldBeFiltered()
    }
}

data class GenderPush(
    override val context: SystemContext,
    override var text: String,
    override var gender: Gender
): IGenderPush;

data class LocationAgePush(
    override val context: SystemContext,
    override var text: String,
    override var x: Float,
    override var y: Float,
    override var radius: Int,
    override var age: Int
): ILocationPush, IAgePush {
    override fun shouldBeFiltered(): Boolean {
        return super<ILocationPush>.shouldBeFiltered()
                || super<IAgePush>.shouldBeFiltered()
    }
}

data class LocationPush(
    override val context: SystemContext,
    override var text: String,
    override var x: Float,
    override var y: Float,
    override var radius: Int,
    override var expiryDate: Long
): ILocationPush, IExpiringPush {
    override fun shouldBeFiltered(): Boolean {
        return super<ILocationPush>.shouldBeFiltered()
                || super<IExpiringPush>.shouldBeFiltered()
    }
}

data class TechPush(
    override val context: SystemContext,
    override var text: String,
    override var osVersion: Int
): ITechPush

/*
package: system.services.push
=============================
 */

interface IPushNotificationService {
    fun appendPush(push: IPush)
    fun filterPushes()

    fun getPushList(): List<IPush>
}

open class PushNotificationService: IPushNotificationService {
    private val pushes = LinkedList<IPush>()

    override fun appendPush(push: IPush) {
        this.pushes.addLast(push)
    }

    override fun filterPushes() {
        val iterator = this.pushes.iterator()

        while (iterator.hasNext()) {
            val item = iterator.next()

            if (item.shouldBeFiltered()) {
                iterator.remove()
            }
        }
    }

    override fun getPushList(): List<IPush> {
        return this.pushes
    }

}

/*
package: system.view.console.adapters
=====================================
 */

object GenderMapper {
    fun fromString(value: String): Gender {
        return when (value) {
            "m" -> Gender.MALE
            "f" -> Gender.FEMALE
            else -> Gender.valueOf(value)
        }
    }

    fun toString(gender: Gender): String {
        when (gender) {
            Gender.MALE -> "m"
            Gender.FEMALE -> "f"
        }
        return ""
    }
}

class ContextAdapter(
    lines: List<String>
): SystemContext() {
    private val setters = mapOf<String, (String) -> Unit>(
        "time" to { value -> this.time = value.toLong() },
        "gender" to { value -> this.user.gender = GenderMapper.fromString(value) },
        "age" to { value -> this.user.age = value.toInt() },
        "os_version" to { value -> this.osVersion = value.toInt() },
        "x_coord" to { value -> this.getGestureListenerService().setX(value.toFloat()) },
        "y_coord" to { value -> this.getGestureListenerService().setY(value.toFloat()) }
    )

    init {
        this.transformData(lines)
    }

    private fun transformData(lines: List<String>) {
        for (line in lines) {
            val words = line.split(' ')
            val setter = setters[words[0]]
            setter?.invoke(words[1])
        }
    }
}

class PushCreator(
    var context: SystemContext
) {
    private val typeMappers = mapOf<String, (PushFieldAdapter) -> IPush>(
        "LocationPush" to { fields -> LocationPush(
            context,
            fields.text!!,
            fields.x!!,
            fields.y!!,
            fields.radius!!,
            fields.expiryDate!!
        ) },
        "AgeSpecificPush" to { fields -> AgeSpecificPush(
            context,
            fields.text!!,
            fields.expiryDate!!,
            fields.age!!
        ) },
        "TechPush" to { fields -> TechPush(
            context,
            fields.text!!,
            fields.osVersion!!
        ) },
        "LocationAgePush" to { fields -> LocationAgePush(
            context,
            fields.text!!,
            fields.x!!,
            fields.y!!,
            fields.radius!!,
            fields.age!!
        ) },
        "GenderAgePush" to { fields -> GenderAgePush(
            context,
            fields.text!!,
            fields.gender!!,
            fields.age!!
        ) },
        "GenderPush" to { fields -> GenderPush(
            context,
            fields.text!!,
            fields.gender!!
        ) },
    )

    fun transformFields(lines: List<String>): PushFieldAdapter {
        val fields = PushFieldAdapter()

        for (line in lines) {
            val words = line.split(' ')
            fields.setField(words[0], words[1])
        }

        return fields
    }

    fun createPush(lines: List<String>): IPush {
        val fields = this.transformFields(lines)
        return typeMappers[fields.type]?.invoke(fields)!!
    }
}

class PushFieldAdapter {
    var type: String? = null
    var text: String? = null
    var expiryDate: Long? = null
    var gender: Gender? = null
    var age: Int? = null
    var x: Float? = null
    var y: Float? = null
    var radius: Int? = null
    var osVersion: Int? = null

    private val mappers = mapOf<String, (String) -> Unit>(
        "type" to { value -> this.type = value },
        "text" to { value -> this.text = value },
        "expiry_date" to { value -> this.expiryDate = value.toLong() },
        "gender" to { value -> this.gender = GenderMapper.fromString(value) },
        "age" to { value -> this.age = value.toInt() },
        "x_coord" to { value -> this.x = value.toFloat() },
        "y_coord" to { value -> this.y = value.toFloat() },
        "radius" to { value -> this.radius = value.toInt() },
        "os_version" to { value -> this.osVersion = value.toInt() }
    )

    fun setField(field: String, value: String) {
        mappers[field]?.invoke(value)
    }
}

/*
package: system.view.console
============================
 */

interface IViewProcess {
    fun onContextCreate()

    fun onStart()
}

class ConsoleView(
    var inputReader: Scanner,
    var outputStream: PrintStream
): IViewProcess {
    private val CONTEXT_LINES_AMOUNT = 6

    private lateinit var context: SystemContext

    override fun onContextCreate() {
        val contextLines = mutableListOf<String>()

        for (lineNumber in 1..CONTEXT_LINES_AMOUNT) {
            contextLines.add(inputReader.nextLine())
        }

        this.context = ContextAdapter(contextLines)
    }

    override fun onStart() {
        val pushCreator = PushCreator(context)

        val pushAmount = inputReader.nextLine().toInt()

        for (push in 1..pushAmount) {
            val paramAmount = inputReader.nextLine().toInt()

            val params = mutableListOf<String>()

            for (param in 1..paramAmount) {
                params.add(inputReader.nextLine())
            }

            val newPush = pushCreator.createPush(params)
            context.getPushNotificationService().appendPush(newPush)
        }

        context.getPushNotificationService().filterPushes()

        val pushes = context.getPushNotificationService().getPushList()

        if (pushes.isEmpty()) {
            outputStream.println(-1)
        }

        for (push in pushes) {
            outputStream.println(push.text)
        }
    }
}

/*
Main function
=============
 */

fun main(args: Array<String>) {
    val system = SystemLauncher()
    system.launch()
}
