import java.util.concurrent.atomic.AtomicLong;

public class StatsManager {

    private final AtomicLong totalRequests =
            new AtomicLong();

    private final AtomicLong cacheHits =
            new AtomicLong();

    private final AtomicLong cacheMisses =
            new AtomicLong();

    public void incrementRequests() {
        totalRequests.incrementAndGet();
    }

    public void incrementCacheHits() {
        cacheHits.incrementAndGet();
    }

    public void incrementCacheMisses() {
        cacheMisses.incrementAndGet();
    }

    public long getTotalRequests() {
        return totalRequests.get();
    }

    public long getCacheHits() {
        return cacheHits.get();
    }

    public long getCacheMisses() {
        return cacheMisses.get();
    }
}