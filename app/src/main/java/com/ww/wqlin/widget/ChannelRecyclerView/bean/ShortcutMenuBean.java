package com.ww.wqlin.widget.ChannelRecyclerView.bean;

public class ShortcutMenuBean {
    private String menu_name = "";
    private int nameResId;

    private String icon;
    private String edit_icon = "";

    private int iconResId;

    private String menu_id;

    private String url; // scheme 分发事件

    private String className;
    private String action;

    private String is_default;
    private String is_selected;

    private String is_hot;
    private String is_new;

    private boolean isAdd;

    private boolean isLaterVisible;
    private boolean isMove;
    private MoveInfo moveInfo;
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIs_default() {
        return is_default;
    }

    public void setIs_default(String is_default) {
        this.is_default = is_default;
    }

    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHot(String hot) {
        is_hot = hot;
    }

    public String is_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getNameResId() {
        return nameResId;
    }

    public void setNameResId(int nameResId) {
        this.nameResId = nameResId;
    }

    public String getMenuName() {
        return menu_name;
    }

    public void setMenuName(String menuName) {
        this.menu_name = menuName;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String isHot() {
        return is_hot;
    }

    public void setIs_hot(String is_hot) {
        this.is_hot = is_hot;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setIsAdd(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public MoveInfo getMoveInfo() {
        return moveInfo;
    }

    public void setMoveInfo(MoveInfo moveInfo) {
        setMove(moveInfo==null?false:true);
        this.moveInfo = moveInfo;
    }

    @Override
    public String toString() {
        return "ShortcutMenuBean{" +
                "menu_name='" + menu_name + '\'' +
                ", nameResId=" + nameResId +
                ", icon='" + icon + '\'' +
                ", iconResId=" + iconResId +
                ", menu_id='" + menu_id + '\'' +
                ", url='" + url + '\'' +
                ", className='" + className + '\'' +
                ", action='" + action + '\'' +
                ", is_default='" + is_default + '\'' +
                ", is_selected='" + is_selected + '\'' +
                ", is_hot='" + is_hot + '\'' +
                ", is_new='" + is_new + '\'' +
                ", isAdd=" + isAdd +
                '}';
    }

    public String getEdit_icon() {
        return edit_icon;
    }

    public void setEdit_icon(String edit_icon) {
        this.edit_icon = edit_icon;
    }

    public boolean isLaterVisible() {
        return isLaterVisible;
    }

    public void setLaterVisible(boolean laterVisible) {
        isLaterVisible = laterVisible;
    }

    public boolean isMove() {
        return isMove;
    }

    private void setMove(boolean move) {
        isMove = move;
    }

    public static class MoveInfo{
        int fromX, fromY,toX,toY;

        public int getFromX() {
            return fromX;
        }

        public void setFromX(int fromX) {
            this.fromX = fromX;
        }

        public int getFromY() {
            return fromY;
        }

        public void setFromY(int fromY) {
            this.fromY = fromY;
        }

        public int getToX() {
            return toX;
        }

        public void setToX(int toX) {
            this.toX = toX;
        }

        public int getToY() {
            return toY;
        }

        public void setToY(int toY) {
            this.toY = toY;
        }
    }
}
