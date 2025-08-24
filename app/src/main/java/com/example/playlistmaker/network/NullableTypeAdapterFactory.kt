import com.google.gson.*
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

class NullableTypeAdapterFactory : TypeAdapterFactory {

    override fun <T : Any> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val delegate = gson.getDelegateAdapter(this, type)

        // Если класс не является типом Kotlin, то используется адаптер по умолчанию
        if (type.rawType.declaredAnnotations.none {
                it.annotationClass.qualifiedName == "kotlin.Metadata" }) {
            return null
        }

        return object : TypeAdapter<T>() {
            override fun write(out: JsonWriter, value: T?) = delegate.write(out, value)

            override fun read(input: JsonReader): T? {
                val value: T? = delegate.read(input)

                if (value != null) {
                    val kotlinClass: KClass<Any> = Reflection.createKotlinClass(type.rawType)

                    // Проверка полей на возможность хранить null-значения
                    kotlinClass.memberProperties.forEach {
                        if (!it.returnType.isMarkedNullable && it.get(value) == null) {
                            return null
                            //throw JsonParseException("Value of non-nullable member [${it.name}] cannot be null")
                        }
                    }

                }
                return value
            }
        }
    }
}