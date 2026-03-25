package ch.Elodin.RealmQuill.model.Family;

public enum RelationType {
    PARTNER, PARENT, CHILD, SIBLING,
    GRANDPARENT, GRANDCHILD,
    PARENT_IN_LAW, CHILD_IN_LAW, SIBLING_IN_LAW;

    public RelationType inverse() {
        return switch (this) {
            case PARENT -> CHILD;
            case CHILD -> PARENT;
            case GRANDPARENT -> GRANDCHILD;
            case GRANDCHILD -> GRANDPARENT;
            case PARTNER -> PARTNER;
            case SIBLING -> SIBLING;
            case PARENT_IN_LAW -> CHILD_IN_LAW;
            case CHILD_IN_LAW -> PARENT_IN_LAW;
            case SIBLING_IN_LAW -> SIBLING_IN_LAW;
        };
    }
}
