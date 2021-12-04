package utility;

public final class JavaModifierSet {
    public static final int PUBLIC = 0x0001;
    public static final int PRIVATE = 0x0002;
    public static final int PROTECTED = 0x0004;

    public boolean isPublic(int modifiers) {
        return (modifiers & PUBLIC) != 0;
    }

    public boolean isPrivate(int modifiers) {
        return (modifiers & PRIVATE) != 0;
    }

    public boolean isProtected(int modifiers) {
        return (modifiers & PROTECTED) != 0;
    }

}
