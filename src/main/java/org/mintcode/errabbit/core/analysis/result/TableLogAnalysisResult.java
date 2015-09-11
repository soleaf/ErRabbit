package org.mintcode.errabbit.core.analysis.result;

import org.mintcode.errabbit.core.analysis.request.AnalysisRequest;

import java.util.*;

/**
 * Created by soleaf on 7/12/15.
 */
public class TableLogAnalysisResult implements AnalysisResult {

    List<Map<String,Object>> list;

    public TableLogAnalysisResult(){

    }

    public TableLogAnalysisResult(AnalysisRequest req, List<Map<String, Object>> list){
        List newList = new ArrayList(list); // unmodifible list
        Collections.sort(newList, new TableLogAnalysisResultComparator(req));
        this.list = newList;
    }

    public List<Map<String,Object>> getResult(){
        return list;
    }

    protected class TableLogAnalysisResultComparator implements Comparator<Map<String,Object>>{

        private AnalysisRequest req;

        public TableLogAnalysisResultComparator(AnalysisRequest req) {
            this.req = req;
        }

        @Override
        public int compare(Map<String, Object> o1, Map<String, Object> o2) {

            StringBuffer o1Index = new StringBuffer();
            StringBuffer o2Index = new StringBuffer();
            for (String column : req.getGroup()){
                if (o1Index.length() > 0){
                    o1Index.append(" ");
                }
                o1Index.append(o1.get(column));

                if (o2Index.length() > 0){
                    o2Index.append(" ");
                }
                o2Index.append(o2.get(column));
            }

            return o1Index.toString().compareTo(o2Index.toString());
        }
    }
}
