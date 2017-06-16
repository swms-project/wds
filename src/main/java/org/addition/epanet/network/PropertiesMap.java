/*
 * Copyright (C) 2012  Addition, Lda. (addition at addition dot pt)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 */

package org.addition.epanet.network;


import org.addition.epanet.Constants;
import org.addition.epanet.network.io.Keywords;
import org.addition.epanet.util.ENException;
import org.addition.epanet.util.Utilities;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Simulation configuration properties map.
 */
public class PropertiesMap {

    /**
     * Flow units.
     */
    static public enum FlowUnitsType {
        AFD(Keywords.w_AFD),         //   acre-feet per day
        CFS(Keywords.w_CFS),         //   cubic feet per second
        CMD(Keywords.w_CMD),         //   cubic meters per day
        CMH(Keywords.w_CMH),         //   cubic meters per hour
        GPM(Keywords.w_GPM),         //   gallons per minute
        IMGD(Keywords.w_IMGD),       //   imperial million gal. per day
        LPM(Keywords.w_LPM),         //   liters per minute
        LPS(Keywords.w_LPS),         //   liters per second
        MGD(Keywords.w_MGD),         //   million gallons per day
        MLD(Keywords.w_MLD);         //   megaliters per day

        public static FlowUnitsType parse(String text) {
            for (FlowUnitsType type : FlowUnitsType.values())
                if (Utilities.match(text, type.parseStr)) return type;
            return null;
        }

        public final String parseStr;

        private FlowUnitsType(String parseStr) {
            this.parseStr = parseStr;
        }
    }

    /**
     * Head loss formula.
     */
    static public enum FormType {
        /**
         * Chezy-Manning
         */
        CM(Keywords.w_CM),
        /**
         * Darcy-Weisbach
         */
        DW(Keywords.w_DW),
        /**
         * Hazen-Williams
         */
        HW(Keywords.w_HW);
        /**
         * Parse string id.
         */
        public final String parseStr;

        FormType(String parseStr) {
            this.parseStr = parseStr;
        }
    }

    /**
     * Hydraulics solution option.
     */
    static public enum Hydtype {
        /**
         * Save after current run.
         */
        SAVE,
        /**
         * Use temporary file.
         */
        SCRATCH,
        /**
         * Use from previous run.
         */
        USE
    }

    /**
     * Pressure units.
     */
    static public enum PressUnitsType {
        KPA(Keywords.w_KPA),        // pounds per square inch
        METERS(Keywords.w_METERS),        // kiloPascals
        PSI(Keywords.w_PSI);  // meters

        public final String parseStr;

        PressUnitsType(String parseStr) {
            this.parseStr = parseStr;
        }
    }

    /**
     * Water quality analysis option.
     */
    static public enum QualType {
        /**
         * Analyze water age.
         */
        AGE(2, Keywords.w_AGE),
        /**
         * Analyze a chemical.
         */
        CHEM(1, Keywords.w_CHEM),
        /**
         * No quality analysis.
         */
        NONE(0, Keywords.w_NONE),
        /**
         * Trace % of flow from a source
         */
        TRACE(3, Keywords.w_TRACE);

        /**
         * Parse quality type from string.
         */
        public static QualType parse(String text) {
            for (QualType type : QualType.values())
                if (Utilities.match(text, type.parseStr)) return type;
            return null;
        }

        /**
         * Sequencial id.
         */
        public final int id;

        /**
         * Parse string id.
         */
        public final String parseStr;

        private QualType(int id, String pStr) {
            this.id = id;
            this.parseStr = pStr;
        }
    }

    /**
     * Reporting flag.
     */
    static public enum ReportFlag {
        FALSE, //0
        SOME, //2,
        // //1
        TRUE
    }

    /**
     * Status report options.
     */
    static public enum StatFlag {
        FALSE(Keywords.w_NO),
        FULL(Keywords.w_FULL),
        TRUE(Keywords.w_YES);

        public static StatFlag parse(String text) {
            for (StatFlag type : StatFlag.values())
                if (Utilities.match(text, type.parseStr)) return type;
            return null;
        }

        public final String parseStr;

        private StatFlag(String parseStr) {
            this.parseStr = parseStr;
        }

    }

    /**
     * Time series statistics.
     */
    static public enum TstatType {
        AVG(Keywords.w_AVG),      // none
        MAX(Keywords.w_MAX),       // time-averages
        MIN(Keywords.w_MIN),       // minimum values
        RANGE(Keywords.w_RANGE),       // maximum values
        SERIES(Keywords.w_NONE);     // max - min values

        public final String parseStr;

        private TstatType(String parseStr) {
            this.parseStr = parseStr;
        }
    }


    /**
     * Unit system.
     */
    static public enum UnitsType {
        /**
         * SI (metric)
         */
        SI,
        /**
         * US
         */
        US
    }

    public static final String ALTREPORT = "AltReport";
    public static final String BULKORDER = "BulkOrder";
    public static final String CHECK_FREQ = "CheckFreq";
    public static final String CHEM_NAME = "ChemName";
    public static final String CHEM_UNITS = "ChemUnits";
    public static final String CLIMIT = "Climit";
    public static final String CTOL = "Ctol";
    public static final String DAMP_LIMIT = "DampLimit";
    public static final String DCOST = "Dcost";
    public static final String DEF_PAT_ID = "DefPatID";
    public static final String DIFFUS = "Diffus";
    public static final String DMULT = "Dmult";
    public static final String DUR = "Dur";
    public static final String ECOST = "Ecost";
    public static final String EMAX = "Emax";
    public static final String ENERGYFLAG = "Energyflag";
    public static final String EPAT_ID = "EpatID";
    public static final String EPUMP = "Epump";
    public static final String EXTRA_ITER = "ExtraIter";
    public static final String FLOWFLAG = "Flowflag";
    public static final String FORMFLAG = "Formflag";
    public static final String HACC = "Hacc";
    public static final String HEXP = "Hexp";
    public static final String HSTEP = "Hstep";
    public static final String HTOL = "Htol";
    public static final String HYD_FNAME = "HydFname";
    public static final String HYDFLAG = "Hydflag";
    public static final String KBULK = "Kbulk";
    public static final String KWALL = "Kwall";
    public static final String LINKFLAG = "Linkflag";
    public static final String MAP_FNAME = "MapFname";
    public static final String MAXCHECK = "MaxCheck";
    public static final String MAXITER = "MaxIter";
    public static final String MESSAGEFLAG = "Messageflag";
    public static final String NODEFLAG = "Nodeflag";
    public static final String PAGE_SIZE = "PageSize";
    public static final String PRESSFLAG = "Pressflag";
    public static final String PSTART = "Pstart";
    public static final String PSTEP = "Pstep";
    public static final String QEXP = "Qexp";
    public static final String QSTEP = "Qstep";
    public static final String QTOL = "Qtol";
    public static final String QUALFLAG = "Qualflag";
    public static final String RFACTOR = "Rfactor";
    public static final String RQTOL = "RQtol";
    public static final String RSTART = "Rstart";
    public static final String RSTEP = "Rstep";
    public static final String RULESTEP = "Rulestep";
    public static final String SPGRAV = "SpGrav";
    public static final String STATFLAG = "Statflag";
    public static final String SUMMARYFLAG = "Summaryflag";
    public static final String TANKORDER = "TankOrder";
    public static final String TRACE_NODE = "TraceNode";
    public static final String TSTART = "Tstart";
    public static final String TSTATFLAG = "Tstatflag";
    public static final String UNITSFLAG = "Unitsflag";
    public static final String VISCOS = "Viscos";
    public static final String WALLORDER = "WallOrder";
    public static final String[] EpanetObjectsNames = {TSTATFLAG, HSTEP, DUR, QSTEP, CHECK_FREQ,
            MAXCHECK, DMULT, ALTREPORT, QEXP, HEXP, RQTOL, QTOL, BULKORDER, TANKORDER, WALLORDER,
            RFACTOR, CLIMIT, KBULK, KWALL, DCOST, ECOST, EPAT_ID, EPUMP, PAGE_SIZE, STATFLAG, SUMMARYFLAG,
            MESSAGEFLAG, ENERGYFLAG, NODEFLAG, LINKFLAG, RULESTEP, PSTEP, PSTART, RSTEP, RSTART, TSTART,
            FLOWFLAG, PRESSFLAG, FORMFLAG, HYDFLAG, QUALFLAG, UNITSFLAG, HYD_FNAME, CHEM_NAME, CHEM_UNITS,
            DEF_PAT_ID, MAP_FNAME, TRACE_NODE, EXTRA_ITER, CTOL, DIFFUS, DAMP_LIMIT, VISCOS, SPGRAV, MAXITER,
            HACC, HTOL, EMAX
    };
    private String altReport;
    private Double bulkOrder;
    private int checkFreq;
    private String chemName;
    private String chemUnits;
    private Double climit;
    private Double ctol;
    private Double dampLimit;
    private Double dcost;
    private String defPatID;
    private Double diffus;
    private Double dmult;
    private long dur;
    private Double ecost;
    private Double emax;
    private boolean energyflag;
    private String epat;
    private Double epump;
    private int extraIter;
    private FlowUnitsType flowflag;
    private FormType formflag;
    private Double hacc;
    private Double hexp;
    private long hstep;
    private Double htol;
    private Hydtype hydflag;
    private String hydFname;
    private Double kbulk;
    private Double kwall;
    private ReportFlag linkflag;
    private String mapFname;
    private int maxCheck;
    private int maxIter;
    private boolean messageflag;
    private ReportFlag nodeflag;
    private int pageSize;
    private PressUnitsType pressflag;
    private long pstart;
    private long pstep;
    private Double qexp;
    private long qstep;
    private Double qtol;
    private QualType qualflag;
    private Double rfactor;
    private Double RQtol;
    private long rstart;
    private long rstep;
    private long rulestep;
    private Double spGrav;
    private StatFlag statflag;
    private boolean summaryflag;
    private Double tankOrder;
    private String traceNode;
    private long tstart;
    private TstatType tstatflag;
    private UnitsType unitsflag;
    private Double viscos;
    private Double wallOrder;
    private Map<String, Object> values;

    public PropertiesMap() {
        values = new HashMap<String, Object>();
        loadDefaults();
    }

    /**
     * Get an object from the map.
     *
     * @param name Object name.
     * @return Object refernce.
     * @throws ENException If object name not found.
     */
    public Object get(String name) throws ENException {
        return values.get(name);
    }

    public String getAltReport() throws ENException {
        return altReport;
    }

    public void setAltReport(String str) throws ENException {
        this.altReport = str;
        values.put(ALTREPORT, str);
    }

    public Double getBulkOrder() throws ENException {
        return bulkOrder;
    }

    public void setBulkOrder(Double bulkOrder) throws ENException {
        this.bulkOrder = bulkOrder;
        values.put(BULKORDER, bulkOrder);
    }

    public Integer getCheckFreq() throws ENException {
        return checkFreq;
    }

    public void setCheckFreq(int checkFreq) throws ENException {
        this.checkFreq = checkFreq;
        values.put(CHECK_FREQ, checkFreq);
    }

    public String getChemName() throws ENException {
        return chemName;
    }

    public void setChemName(String chemName) throws ENException {
        this.chemName = chemName;
        values.put(CHEM_NAME, chemName);
    }

    public String getChemUnits() throws ENException {
        return chemUnits;
    }

    public void setChemUnits(String chemUnits) throws ENException {
        this.chemUnits = chemUnits;
        values.put(CHEM_UNITS, chemUnits);
    }

    public Double getClimit() throws ENException {
        return climit;
    }

    public void setClimit(Double climit) throws ENException {
        this.climit = climit;
        values.put(CLIMIT, climit);
    }

    public Double getCtol() throws ENException {
        return ctol;
    }

    public void setCtol(Double ctol) throws ENException {
        this.ctol = ctol;
        values.put(CTOL, ctol);
    }

    public Double getDampLimit() throws ENException {
        return dampLimit;
    }

    public void setDampLimit(Double dampLimit) throws ENException {
        this.dampLimit = dampLimit;
        values.put(DAMP_LIMIT, dampLimit);
    }

    public Double getDcost() throws ENException {
        return dcost;
    }

    public void setDcost(Double dcost) throws ENException {
        this.dcost = dcost;
        values.put(DCOST, dcost);
    }

    public String getDefPatId() throws ENException {
        return defPatID;
    }

    public Double getDiffus() throws ENException {
        return diffus;
    }

    public void setDiffus(Double diffus) throws ENException {
        this.diffus = diffus;
        values.put(DIFFUS, diffus);
    }

    public Double getDmult() throws ENException {
        return dmult;
    }

    public void setDmult(Double dmult) throws ENException {
        this.dmult = dmult;
        values.put(DMULT, dmult);
    }

    public Long getDuration() throws ENException {
        return dur;
    }

    public void setDuration(long dur) throws ENException {
        this.dur = dur;
        values.put(DUR, dur);
    }

    public Double getEcost() throws ENException {
        return ecost;
    }

    public void setEcost(Double ecost) throws ENException {
        this.ecost = ecost;
        values.put(ECOST, ecost);
    }

    public Double getEmax() throws ENException {
        return emax;
    }

    public void setEmax(Double emax) throws ENException {
        this.emax = emax;
        values.put(EMAX, emax);
    }

    public Boolean getEnergyflag() throws ENException {
        return energyflag;
    }

    public void setEnergyflag(boolean energyflag) throws ENException {
        this.energyflag = energyflag;
        values.put(ENERGYFLAG, energyflag);
    }

    public String getEpatId() throws ENException {
        return epat;
    }

    public Double getEpump() throws ENException {
        return epump;
    }

    public void setEpump(Double epump) throws ENException {
        this.epump = epump;
        values.put(EPUMP, epump);
    }

    public Integer getExtraIter() throws ENException {
        return extraIter;
    }

    public void setExtraIter(int extraIter) throws ENException {
        this.extraIter = extraIter;
        values.put(EXTRA_ITER, extraIter);
    }

    public FlowUnitsType getFlowflag() throws ENException {
        return flowflag;
    }

    public void setFlowflag(FlowUnitsType flowflag) throws ENException {
        this.flowflag = flowflag;
        values.put(FLOWFLAG, flowflag);
    }

    public FormType getFormflag() throws ENException {
        return formflag;
    }

    public void setFormflag(FormType formflag) throws ENException {
        this.formflag = formflag;
        values.put(FORMFLAG, formflag);
    }

    public Double getHacc() throws ENException {
        return hacc;
    }

    public void setHacc(Double hacc) throws ENException {
        this.hacc = hacc;
        values.put(HACC, hacc);
    }

    public Double getHexp() throws ENException {
        return hexp;
    }

    public void setHexp(Double hexp) throws ENException {
        this.hexp = hexp;
        values.put(HEXP, hexp);
    }

    public Long getHstep() throws ENException {
        return hstep;
    }

    public void setHstep(long hstep) throws ENException {
        this.hstep = hstep;
        values.put(HSTEP, hstep);
    }

    public Double getHtol() throws ENException {
        return htol;
    }

    public void setHtol(Double htol) throws ENException {
        this.htol = htol;
        values.put(HTOL, htol);
    }

    public Hydtype getHydflag() throws ENException {
        return hydflag;
    }

    public void setHydflag(Hydtype hydflag) throws ENException {
        this.hydflag = hydflag;
        values.put(HYDFLAG, hydflag);
    }

    public String getHydFname() throws ENException {
        return hydFname;
    }

    public void setHydFname(String hydFname) throws ENException {
        this.hydFname = hydFname;
        values.put(HYD_FNAME, hydFname);
    }

    public Double getKbulk() throws ENException {
        return kbulk;
    }

    public void setKbulk(Double kbulk) throws ENException {
        this.kbulk = kbulk;
        values.put(KBULK, kbulk);
    }

    public Double getKwall() throws ENException {
        return kwall;
    }

    public void setKwall(Double kwall) throws ENException {
        this.kwall = kwall;
        values.put(KWALL, kwall);
    }

    public ReportFlag getLinkflag() throws ENException {
        return linkflag;
    }

    public void setLinkflag(ReportFlag linkflag) throws ENException {
        this.linkflag = linkflag;
        values.put(LINKFLAG, linkflag);
    }

    public String getMapFname() throws ENException {
        return mapFname;
    }

    public void setMapFname(String mapFname) throws ENException {
        this.mapFname = mapFname;
        values.put(MAP_FNAME, mapFname);
    }

    public Integer getMaxCheck() throws ENException {
        return maxCheck;
    }

    public void setMaxCheck(int maxCheck) throws ENException {
        this.maxCheck = maxCheck;
        values.put(MAXCHECK, maxCheck);
    }

    public Integer getMaxIter() throws ENException {
        return maxIter;
    }

    public void setMaxIter(int maxIter) throws ENException {
        this.maxIter = maxIter;
        values.put(MAXITER, maxIter);
    }

    public Boolean getMessageflag() throws ENException {
        return messageflag;
    }

    public void setMessageflag(boolean messageflag) throws ENException {
        this.messageflag = messageflag;
        values.put(MESSAGEFLAG, messageflag);
    }

    public ReportFlag getNodeflag() throws ENException {
        return nodeflag;
    }

    public void setNodeflag(ReportFlag nodeflag) throws ENException {
        this.nodeflag = nodeflag;
        values.put(NODEFLAG, nodeflag);
    }

    /**
     * Get objects names in this map.
     *
     * @param exclude_epanet exclude Epanet objects.
     * @return List of objects names.
     */
    public List<String> getObjectsNames(boolean exclude_epanet) {
        List<String> allObjs = new ArrayList<String>(values.keySet());
        if (exclude_epanet)
            allObjs.removeAll(Arrays.asList(EpanetObjectsNames));
        return allObjs;
    }

    public Integer getPageSize() throws ENException {
        return pageSize;
    }

    public void setPageSize(int pageSize) throws ENException {
        this.pageSize = pageSize;
        values.put(PAGE_SIZE, pageSize);
    }

    public PressUnitsType getPressflag() throws ENException {
        return pressflag;
    }

    public void setPressflag(PressUnitsType pressflag) throws ENException {
        this.pressflag = pressflag;
        values.put(PRESSFLAG, pressflag);
    }

    public Long getPstart() throws ENException {
        return pstart;
    }

    public void setPstart(long pstart) throws ENException {
        this.pstart = pstart;
        values.put(PSTART, pstart);
    }

    public Long getPstep() throws ENException {
        return pstep;
    }

    public void setPstep(long pstep) throws ENException {
        this.pstep = pstep;
        values.put(PSTEP, pstep);
    }

    public Double getQexp() throws ENException {
        return qexp;
    }

    public void setQexp(Double qexp) throws ENException {
        this.qexp = qexp;
        values.put(QEXP, qexp);
    }

    public Long getQstep() throws ENException {
        return qstep;
    }

    public void setQstep(long qstep) throws ENException {
        this.qstep = qstep;
        values.put(QSTEP, qstep);
    }

    public Double getQtol() throws ENException {
        return qtol;
    }

    public void setQtol(Double qtol) throws ENException {
        this.qtol = qtol;
        values.put(QTOL, qtol);
    }

    public QualType getQualflag() throws ENException {
        return qualflag;
    }

    public void setQualflag(QualType qualflag) throws ENException {
        this.qualflag = qualflag;
        values.put(QUALFLAG, qualflag);
    }

    public Double getRfactor() throws ENException {
        return rfactor;
    }

    public void setRfactor(Double rfactor) throws ENException {
        this.rfactor = rfactor;
        values.put(RFACTOR, rfactor);
    }

    public Double getRQtol() throws ENException {
        return RQtol;
    }

    public void setRQtol(Double RQtol) throws ENException {
        this.RQtol = RQtol;
        values.put(RQTOL, RQtol);
    }

    public Long getRstart() throws ENException {
        return rstart;
    }

    public void setRstart(long rstart) throws ENException {
        this.rstart = rstart;
        values.put(RSTART, rstart);
    }

    public Long getRstep() throws ENException {
        return rstep;
    }

    public void setRstep(long rstep) throws ENException {
        this.rstep = rstep;
        values.put(RSTEP, rstep);
    }

    public Long getRulestep() throws ENException {
        return rulestep;
    }

    public void setRulestep(long rulestep) throws ENException {
        this.rulestep = rulestep;
        values.put(RULESTEP, rulestep);
    }

    public Double getSpGrav() throws ENException {
        return spGrav;
    }

    public void setSpGrav(Double spGrav) throws ENException {
        this.spGrav = spGrav;
        values.put(SPGRAV, spGrav);
    }

    public StatFlag getStatflag() throws ENException {
        return statflag;
    }

    public void setStatflag(StatFlag statflag) throws ENException {
        this.statflag = statflag;
        values.put(STATFLAG, statflag);
    }

    public Boolean getSummaryflag() throws ENException {
        return summaryflag;
    }

    public void setSummaryflag(boolean summaryflag) throws ENException {
        this.summaryflag = summaryflag;
        values.put(SUMMARYFLAG, summaryflag);
    }

    public Double getTankOrder() throws ENException {
        return tankOrder;
    }

    public void setTankOrder(Double tankOrder) throws ENException {
        this.tankOrder = tankOrder;
        values.put(TANKORDER, tankOrder);
    }

    public String getTraceNode() throws ENException {
        return traceNode;
    }

    public void setTraceNode(String traceNode) throws ENException {
        this.traceNode = traceNode;
        values.put(TRACE_NODE, traceNode);
    }

    public Long getTstart() throws ENException {
        return tstart;
    }

    public void setTstart(long tstart) throws ENException {
        this.tstart = tstart;
        values.put(TSTART, tstart);
    }

    public TstatType getTstatflag() throws ENException {
        return tstatflag;
    }

    public void setTstatflag(TstatType tstatflag) throws ENException {
        this.tstatflag = tstatflag;
        values.put(TSTATFLAG, tstatflag);
    }

    public UnitsType getUnitsflag() throws ENException {
        return unitsflag;
    }

    public void setUnitsflag(UnitsType unitsflag) throws ENException {
        this.unitsflag = unitsflag;
        values.put(UNITSFLAG, unitsflag);
    }

    public Double getViscos() throws ENException {
        return viscos;
    }

    public void setViscos(Double viscos) throws ENException {
        this.viscos = viscos;
        values.put(VISCOS, viscos);
    }

    public Double getWallOrder() throws ENException {
        return wallOrder;
    }

    public void setWallOrder(Double wallOrder) throws ENException {
        this.wallOrder = wallOrder;
        values.put(WALLORDER, wallOrder);
    }

    /**
     * Init properties with default value.
     */
    private void loadDefaults() {
        put(BULKORDER, new Double(1.0d));     // 1st-order bulk reaction rate
        put(TANKORDER, new Double(1.0d));     // 1st-order tank reaction rate
        put(WALLORDER, new Double(1.0d));     // 1st-order wall reaction rate
        put(RFACTOR, new Double(1.0d));     // No roughness-reaction factor
        put(CLIMIT, new Double(0.0d));     // No limiting potential quality
        put(KBULK, new Double(0.0d));     // No global bulk reaction
        put(KWALL, new Double(0.0d));     // No global wall reaction
        put(DCOST, new Double(0.0d));     // Zero energy demand charge
        put(ECOST, new Double(0.0d));     // Zero unit energy cost
        put(EPAT_ID, "");                   // No energy price pattern
        put(EPUMP, Constants.EPUMP);      // Default pump efficiency
        put(PAGE_SIZE, Constants.PAGESIZE);
        put(STATFLAG, StatFlag.FALSE);
        put(SUMMARYFLAG, true);
        put(MESSAGEFLAG, true);
        put(ENERGYFLAG, false);
        put(NODEFLAG, ReportFlag.FALSE);
        put(LINKFLAG, ReportFlag.FALSE);
        put(TSTATFLAG, TstatType.SERIES);     // Generate time series output
        put(HSTEP, new Long(3600));       // 1 hr hydraulic time step
        put(DUR, new Long(0));          // 0 sec duration (steady state)
        put(QSTEP, new Long(0));          // No pre-set quality time step
        put(RULESTEP, new Long(0));          // No pre-set rule time step
        put(PSTEP, new Long(3600));       // 1 hr time pattern period
        put(PSTART, new Long(0));          // Starting pattern period
        put(RSTEP, new Long(3600));       // 1 hr reporting period
        put(RSTART, new Long(0));          // Start reporting at time 0
        put(TSTART, new Long(0));          // Starting time of day
        put(FLOWFLAG, FlowUnitsType.GPM);    // Flow units are gpm
        put(PRESSFLAG, PressUnitsType.PSI);   // Pressure units are psi
        put(FORMFLAG, FormType.HW);          // Use Hazen-Williams formula
        put(HYDFLAG, Hydtype.SCRATCH);      // No external hydraulics file
        put(QUALFLAG, QualType.NONE);        // No quality simulation
        put(UNITSFLAG, UnitsType.US);         // US unit system
        put(HYD_FNAME, "");
        put(CHEM_NAME, Keywords.t_CHEMICAL);
        put(CHEM_UNITS, Keywords.u_MGperL);    // mg/L
        put(DEF_PAT_ID, Constants.DEFPATID);   // Default demand pattern index
        put(MAP_FNAME, "");
        put(ALTREPORT, "");
        put(TRACE_NODE, "");                   // No source tracing
        put(EXTRA_ITER, new Integer(-1));      // Stop if network unbalanced
        put(CTOL, Constants.MISSING);    // No pre-set quality tolerance
        put(DIFFUS, Constants.MISSING);    // Temporary diffusivity
        put(DAMP_LIMIT, Constants.DAMPLIMIT);
        put(VISCOS, Constants.MISSING);    // Temporary viscosity
        put(SPGRAV, Constants.SPGRAV);     // Default specific gravity
        put(MAXITER, Constants.MAXITER);    // Default max. hydraulic trials
        put(HACC, Constants.HACC);       // Default hydraulic accuracy
        put(HTOL, Constants.HTOL);       // Default head tolerance
        put(QTOL, Constants.QTOL);       // Default flow tolerance
        put(RQTOL, Constants.RQTOL);      // Default hydraulics parameters
        put(HEXP, new Double(0.0d));
        put(QEXP, new Double(2.0d));     // Flow exponent for emitters
        put(CHECK_FREQ, Constants.CHECKFREQ);
        put(MAXCHECK, Constants.MAXCHECK);
        put(DMULT, new Double(1.0d));     // Demand multiplier
        put(EMAX, new Double(0.0d));     // Zero peak energy usage


    }

    /**
     * Insert an object into the map.
     *
     * @param name Object name.
     * @param obj  Object reference.
     */
    public void put(String name, Object obj) {
        Method method = getSetter(name, obj);
        try {
            method.invoke(this, obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Method getSetter(String name, Object obj) {
        String methodName = "set" + name;
        Method method;
        try {
            method = getClass().getMethod(methodName, obj.getClass());
        } catch (NoSuchMethodException e) {
            try {
                Field fieldType = obj.getClass().getField("TYPE");
                Class cls = (Class) fieldType.get(obj.getClass());
                method = getClass().getMethod(methodName, cls);
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return method;
    }

    public void setDefPatID(String defPatID) throws ENException {
        this.defPatID = defPatID;
        values.put(DEF_PAT_ID, defPatID);
    }

    public void setDur(long dur) throws ENException {
        setDuration(dur);
    }

    public void setEpatID(String epat) throws ENException {
        this.epat = epat;
        values.put(EPAT_ID, epat);
    }
}
