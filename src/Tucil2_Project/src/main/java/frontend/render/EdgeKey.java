package frontend.render;

import java.util.Objects;

public class EdgeKey {
    private final int a;
    private final int b;

    public EdgeKey(int v1, int v2) {
        if (v1 < v2) {
            this.a = v1;
            this.b = v2;
        } 
        else {
            this.a = v2;
            this.b = v1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof EdgeKey)) return false;
        EdgeKey other = (EdgeKey) obj;
        return a == other.a && b == other.b;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}