package jp.ac.mayoi.phone.model.enums

import jp.ac.mayoi.phone.model.interfaces.StringEnum
import jp.ac.mayoi.phone.model.interfaces.StringEnumAdapter

object ServiceAreaAdapter : StringEnumAdapter<ServiceAreaAdapter.ServiceArea>() {
    override val default: ServiceArea
        get() = ServiceArea.UNKNOWN

    override fun fromRemote(remoteToken: String): ServiceArea =
        enumValues<ServiceArea>().find { it.remoteToken == remoteToken } ?: default

    enum class ServiceArea : StringEnum<ServiceArea> {
        HAKODATE_STA {
            override val localToken: String
                get() = localPrefix + "HAKODATE_STATION"
            override val remoteToken: String
                get() = "ekimae"
        },
        HAKODATE_BAY {
            override val localToken: String
                get() = localPrefix + "HAKODATE_BAY"
            override val remoteToken: String
                get() = "bay"
        },
        MT_HAKODATE {
            override val localToken: String
                get() = localPrefix + "MT_HAKODATE"
            override val remoteToken: String
                get() = "hakodateyama"
        },
        GORYOKAKU {
            override val localToken: String
                get() = localPrefix + "GORYOKAKU"
            override val remoteToken: String
                get() = "goryokaku"
        },
        YUNOKAWA {
            override val localToken: String
                get() = localPrefix + "YUNOKAWA"
            override val remoteToken: String
                get() = "yunokawa"
        },
        MIHARA {
            override val localToken: String
                get() = localPrefix + "HAKODATE_MIHARA"
            override val remoteToken: String
                get() = "mihara"
        },
        UNKNOWN {
            override val localToken: String
                get() = "UNKNOWN"
            override val remoteToken: String
                get() = "UNKNOWN"
        };

        internal val localPrefix = "MAIGO_COMPASS_SERVICE_AREA_"
    }
}
