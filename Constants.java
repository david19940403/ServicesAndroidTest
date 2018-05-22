package clin.com.binding;
/**
 * Contiene las constantes de las acciones de los servicios y sus parámetros
 */
public class Constants {
    /**
     * Constantes para {@link MemoryService}
     */
    public static final String ACTION_RUN_SERVICE = "com.herprogramacion.memoryout.action.RUN_SERVICE";
    public static final String ACTION_MEMORY_EXIT = "com.herprogramacion.memoryout.action.MEMORY_EXIT";

    public static final String EXTRA_MEMORY = "com.herprogramacion.memoryout.extra.MEMORY";

    /**
     * Constantes para {@link ProgressIntentService}
     */
    public static final String ACTION_RUN_ISERVICE = "com.herprogramacion.memoryout.action.RUN_INTENT_SERVICE";
    public static final String ACTION_PROGRESS_EXIT = "com.herprogramacion.memoryout.action.PROGRESS_EXIT";

    public static final String EXTRA_PROGRESS = "com.herprogramacion.memoryout.extra.PROGRESS";

    /**
     * Servicio Externo
     */
    public static final String ACTION_RUN_LOCATIONSERVICE="com.clinpaysdelivery.action.IntentService";
    public static final String EXTRA_LOCATION="com.clinpaysdelivery.extraLocation";
    public static final String ACTION_LOCATION_EXIT = "com.clinpaysdelivery.action.PROGRESS_EXIT";

}