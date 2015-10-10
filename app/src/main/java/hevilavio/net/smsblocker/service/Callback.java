package hevilavio.net.smsblocker.service;

/**
 *
 * Responsável por executar uma ação qualquer
 * após um processo assincrono.
 *
 * Created by hevilavio on 10/9/15.
 */
public interface Callback<T> {

    void execute(T t);
}
