package calculating;

/**
 * Created by keyangzheng on 9/15/16.
 */
public enum State {
    READY_FIRSTNUM,
    IN_FIRSTNUM,
    READY_OPERATOR,
    READY_SECONDNUM,
    IN_SECONDNUM,
    READY_EQUAL;

//    public State nextStatus(){
//        switch (this) {
//            case READY_FIRSTNUM:  return READY_OPERATOR;
//            case READY_OPERATOR:  return READY_SECONDNUM;
//            case READY_SECONDNUM: return READY_EQUAL;
//            case READY_EQUAL:     return READY_FIRSTNUM;
//            default:              return READY_FIRSTNUM;
//        }
//    }
}
