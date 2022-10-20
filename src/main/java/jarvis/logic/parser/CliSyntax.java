package jarvis.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("s/");
    public static final Prefix PREFIX_MATRIC_NUM = new Prefix("m/");
    public static final Prefix PREFIX_TASK_DESC = new Prefix("t/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("d/");
    public static final Prefix PREFIX_MC_NUM = new Prefix("num/");
    public static final Prefix PREFIX_MC_RES = new Prefix("r/");

    public static final Prefix PREFIX_RA1 = new Prefix("ra1/");
    public static final Prefix PREFIX_RA2 = new Prefix("ra2/");
    public static final Prefix PREFIX_MIDTERM = new Prefix("mt/");
    public static final Prefix PREFIX_PRACTICAL_ASST = new Prefix("pa/");
    public static final Prefix PREFIX_FINAL_ASST = new Prefix("fn/");
}
