# Utils
Flow<*>.filterIsInstance<R> : Trả về luồng chỉ chứa các giá trị là phiên bản của loại R được chỉ định.
Flow<T>.distinctUntilChanged() : loại bỏ các giá trị trùng lặp liên tiếp
Flow<T>.shareIn(scope: CoroutineScope, started: SharingStarted, replay: Int = 0):  
+ scope - the coroutine scope in which sharing is started.
+ started - the strategy that controls when sharing is started and stopped.
+ replay - the number of values replayed to new subscribers (cannot be negative, defaults to zero).
