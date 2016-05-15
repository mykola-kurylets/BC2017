package TableHolder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Mykola on 14.05.2016.
 */
public class GunSystemLoader extends DefaultHandler
{
    public GunSystemLoader()
    {
        m_Gs = null;

        m_Guns = null;
        m_BulletProp = null;
        m_SightProp = null;
        m_NormalConditions = null;
        m_OverestimationTbl = null;
        m_CorrectionsTbl = null;
        m_SystemName = null;
    }

    GunSystemLoader(GunSystemHolder gs)
    {
        m_Gs = gs;

        m_Guns = m_Gs.GetGunsVec();
        m_BulletProp = m_Gs.GetBullet();
        m_SightProp = m_Gs.GetSight();
        m_NormalConditions = m_Gs.GetNormal();
        m_OverestimationTbl = m_Gs.GetOverestimationTbl();
        m_CorrectionsTbl = m_Gs.GetCorrectionTbl();
        m_SystemName = null;
    }

    boolean Load(String filePath)
    {
        m_Guns.clear();
        m_OverestimationTbl.clear();
        m_CorrectionsTbl.clear();

        try {

            File fXml = new File(filePath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXml);
            doc.getDocumentElement().normalize();

            // отримати список вузлів
            NodeList nList = doc.getElementsByTagName(m_SystemTag);

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node systemNode = nList.item(temp);

                if (systemNode.getNodeType() != Node.ELEMENT_NODE)
                    continue;

                m_SystemName = GetStringNodeAttrib(systemNode, m_NameAtrr);
                m_Gs.SetName(m_SystemName);

                if(!ReadProperties(systemNode))
                    return  false;

                if(!ReadNormal(systemNode))
                    return false;

                if(!ReadOverestimationTbl(systemNode))
                    return false;

                if(!ReadCorrectionTbl(systemNode))
                    return false;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    // метод для отримання імені системи
    // використовується SAX parser
    public String LoadSystemName(String filePath)
    {
        try {
            File inputFile = new File(filePath);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse(inputFile, this);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return m_SystemName;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equalsIgnoreCase(m_SystemTag)) {
            m_SystemName = attributes.getValue(m_NameAtrr);
        }
    }

    private boolean ReadProperties(Node systemNode)
    {
        Node propsNode = GetChildNodeByName(systemNode, m_ProperiesTag);

        if(!ReadGuns(propsNode))
            return false;

        if(!ReadBullet(propsNode))
            return false;

        return ReadSight(propsNode);
    }

    private boolean ReadGuns(Node propsNode)
    {
        if(propsNode == null)
            return false;

        NodeList propsNodes = propsNode.getChildNodes();

        if(propsNodes == null)
            return false;

        for(int i = 0, stape = propsNodes.getLength(); i < stape; ++i){

            Node gunNode = propsNodes.item(i);

            if(gunNode == null)
                continue;

            String nodeName = gunNode.getNodeName();
            if(nodeName == null)
                continue;

            if(!nodeName.equals(m_GunTag))
                continue;

            String gunName = GetStringNodeAttrib(gunNode, m_NameAtrr);

            if(gunName == null)
                continue;

            m_Guns.add(gunName);

        }

        return true;
    }

    private boolean ReadBullet(Node propsNode)
    {
        if(propsNode == null)
            return false;

        Node bulletNode = GetChildNodeByName(propsNode, m_BulletTag);

        if(bulletNode == null)
            return  false;

        m_BulletProp.m_Name = GetStringNodeAttrib(bulletNode, m_NameAtrr);

        NodeList bulletProps = bulletNode.getChildNodes();

        for(int i = 0, stape = bulletProps.getLength(); i < stape; ++i){
            Node node = bulletProps.item(i);

            if(node == null)
                continue;

            String nodeName = node.getNodeName();
            if(nodeName == null)
                continue;

            if(nodeName.equals(m_CalTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_BulletProp.m_Caliber = val;

                continue;
            }

            if(nodeName.equals(m_SpeedTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_BulletProp.m_Speed = val;

                continue;
            }

            if(nodeName.equals(m_WeightTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_BulletProp.m_Weight = val;

                continue;
            }

            if(nodeName.equals(m_DescriptionTag)) {
                    m_BulletProp.m_Description = GetStringNodeAttrib(node, m_ValAtrr);
            }
        }

        return true;
    }

    private boolean ReadSight(Node propsNode)
    {
        if(propsNode == null)
            return false;

        Node sightNode = GetChildNodeByName(propsNode, m_SightTag);

        if(sightNode == null)
            return  false;

        m_SightProp.m_Name = GetStringNodeAttrib(sightNode, m_NameAtrr);

        NodeList sightProps = sightNode.getChildNodes();

        for(int i = 0, stape = sightProps.getLength(); i < stape; ++i) {
            Node node = sightProps.item(i);

            if (node == null)
                continue;

            String nodeName = node.getNodeName();
            if (nodeName == null)
                continue;

            if(nodeName.equals(m_PriceDivisionTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_SightProp.m_PriceDivision = val;

                continue;
            }

            if(nodeName.equals(m_priceClickTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_SightProp.m_PriceClick = val;

            }
        }

        return true;
    }

    private boolean ReadNormal(Node systemNode)
    {
        Node normalNode = GetChildNodeByName(systemNode, m_NormalTag);

        if(normalNode == null)
            return false;

        NodeList normalNodes = normalNode.getChildNodes();

        for(int i = 0, stape = normalNodes.getLength(); i < stape; ++i) {
            Node node = normalNodes.item(i);

            if (node == null)
                continue;

            String nodeName = node.getNodeName();
            if (nodeName == null)
                continue;

            if(nodeName.equals(m_TargetAngleTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_NormalConditions.m_TargetAngle = val;

                continue;
            }

            if(nodeName.equals(m_LateralTiltTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_NormalConditions.m_LateralTilt = val;

                continue;
            }

            if(nodeName.equals(m_PressureTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_NormalConditions.m_Pressure = val;

                continue;
            }

            if(nodeName.equals(m_AirTemperatureTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_NormalConditions.m_AirTemperature = val;

                continue;
            }

            if(nodeName.equals(m_RelativeHumidityTag)) {
                Double val = GetDoubleAttrib(node, m_ValAtrr);
                if(val != null)
                    m_NormalConditions.m_RelativeHumidity = val;
            }
        }

        return true;
    }

    private boolean ReadOverestimationTbl(Node systemNode)
    {
        Node overNode = GetChildNodeByName(systemNode, m_OverestimationTblTag);
        if(overNode == null)
            return false;

        NodeList sightNodes = overNode.getChildNodes();

        if(sightNodes == null)
            return false;

        for(int i = 0, stape = sightNodes.getLength(); i < stape; ++i){
            Node sightNode = sightNodes.item(i);

            if (sightNode == null)
                continue;

            String nodeName = sightNode.getNodeName();
            if (nodeName == null)
                continue;

            if(!nodeName.equals(m_SightTag))
                continue;

            SightParams sPrms = ReadOvereSight(sightNode);

            if(sPrms == null)
                continue;

            m_OverestimationTbl.add(sPrms);
        }

        return true;
    }

    private SightParams ReadOvereSight(Node sightNode)
    {
        Integer sNum = GetIntegerAttrib(sightNode, m_NumAttr);

        if(sNum == null)
            return null;

        NodeList distList = sightNode.getChildNodes();

        if(distList == null)
            return null;

        DistOverVecPair overVerPairs = new DistOverVecPair();
        for(int i = 0, stape = distList.getLength(); i < stape; ++i){
            Node distNode = distList.item(i);

            if (distNode == null)
                continue;

            String nodeName = distNode.getNodeName();
            if (nodeName == null)
                continue;

            if(!nodeName.equals(m_DistTag))
                continue;

            Integer dist = GetIntegerAttrib(distNode, m_ValAtrr);

            if(dist == null)
                continue;

            Double over = GetDoubleAttrib(distNode, m_OverestimationAtrr);

            if(over == null)
                continue;

            overVerPairs.add(new DistOverPair(dist, over));
        }

        if(overVerPairs.isEmpty())
            return null;

        SightParams sp = new SightParams(sNum, (DistOverVecPair)overVerPairs.clone());

        overVerPairs.clear();

        return sp;
    }


    private boolean ReadCorrectionTbl(Node systemNode)
    {
        Node corrNode = GetChildNodeByName(systemNode, m_CorrectionTblTag);
        if(corrNode == null)
            return false;

        NodeList distNodes = corrNode.getChildNodes();

        if(distNodes == null)
            return false;

        for(int i = 0, stape = distNodes.getLength(); i < stape; ++i) {
            Node distNode = distNodes.item(i);

            if (distNode == null)
                continue;

            String nodeName = distNode.getNodeName();
            if (nodeName == null)
                continue;

            if (!nodeName.equals(m_DistTag))
                continue;

            MeteoBalisticCorrectionPair mbcp = ReadMeteoBalPair(distNode);

            if(mbcp == null)
                continue;

            m_CorrectionsTbl.add(mbcp);
        }

        return true;
    }

    private MeteoBalisticCorrectionPair ReadMeteoBalPair(Node distNode)
    {
        Integer distVal = GetIntegerAttrib(distNode, m_ValAtrr);
        if(distVal == null)
            return null;

        Node distCorr = GetChildNodeByName(distNode, m_DistCorrTag);
        if(distCorr == null)
            return null;

        MeteoBalisticCorrection mbCor = new MeteoBalisticCorrection();

        if(!ReadCorrection(distCorr, mbCor.GetDistanceCorrection()))
            return null;

        Node heightCorr = GetChildNodeByName(distNode, m_HeightCorrTag);
        if(heightCorr == null)
            return null;

        if(!ReadCorrection(heightCorr, mbCor.GetHeightCorrection()))
            return null;

        Node dirNode = GetChildNodeByName(distNode, m_DirectionCorrTag);
        if(dirNode == null)
            return null;

        if(!ReadDirrectionCorr(dirNode, mbCor.GetDirectionCorrection()))
            return null;

        return new MeteoBalisticCorrectionPair(distVal, mbCor);
    }

    private boolean ReadCorrection(Node corrNode, Correction corr)
    {
        NodeList corrList = corrNode.getChildNodes();

        if(corrList == null)
            return false;

        for(int i = 0, stape = corrList.getLength(); i < stape; ++i) {
            Node sumNode = corrList.item(i);

            if (sumNode == null)
                continue;

            String nodeName = sumNode.getNodeName();
            if (nodeName == null)
                continue;

            if(nodeName.equals(m_TempTag)){
                Double val = GetDoubleAttrib(sumNode, m_ValAtrr);
                if(val != null)
                    corr.SetTemperature(val);
                continue;
            }

            if(nodeName.equals(m_PressureTag)){
                Double val = GetDoubleAttrib(sumNode, m_ValAtrr);
                if(val != null)
                    corr.SetPressure(val);
                continue;
            }

            if(nodeName.equals(m_SpeedTag)){
                Double val = GetDoubleAttrib(sumNode, m_ValAtrr);
                if(val != null)
                    corr.SetSpeed(val);
                continue;
            }

            if(nodeName.equals(m_WindTag)){
                WindPair wp = ReadWindPair(sumNode);
                if(wp != null)
                    corr.AddWind(wp);
            }

        }

        return true;
    }

    private WindPair ReadWindPair(Node windNode)
    {
        Integer speedVal = GetIntegerAttrib(windNode, m_SpeedAtrr);
        if(speedVal == null)
            return null;

        Double devVal = GetDoubleAttrib(windNode, m_DeviationAtrr);
        if(devVal == null)
            return null;

        return new WindPair(speedVal, devVal);
    }

    private boolean ReadDirrectionCorr(Node dirNode, DirrectionCorrection dirCorr)
    {
        NodeList dirList = dirNode.getChildNodes();

        if(dirList == null)
            return false;

        for(int i = 0, stape = dirList.getLength(); i < stape; ++i) {
            Node sumNode = dirList.item(i);

            if (sumNode == null)
                continue;

            String nodeName = sumNode.getNodeName();
            if (nodeName == null)
                continue;

            if(nodeName.equals(m_WindTag)){
                WindPair wp = ReadWindPair(sumNode);
                if(wp != null)
                    dirCorr.AddWind(wp);
                continue;
            }

            if(nodeName.equals(m_DerivationTag)){
                Double val = GetDoubleAttrib(sumNode, m_ValAtrr);
                if(val != null)
                    dirCorr.SetDerivation(val);
            }
        }

        return true;
    }

    private String GetStringNodeAttrib(Node nod, String attribName)
    {
        if(!(nod instanceof Element))
            return null;

        Element eElement = (Element)nod;

        return eElement.getAttribute(attribName);
    }

    private Double GetDoubleAttrib(Node nod, String attribName)
    {
        String attrib = GetStringNodeAttrib(nod, attribName);
        if(attrib == null)
            return null;

        return new Double(attrib);
    }

    private Integer GetIntegerAttrib(Node nod, String attribName)
    {
        String attrib = GetStringNodeAttrib(nod, attribName);
        if(attrib == null)
            return null;

        return new Integer(attrib);
    }

    private Node GetChildNodeByName(Node parent, String name)
    {
        NodeList list = parent.getChildNodes();

        if(list == null)
            return null;

        for(int i = 0, stape = list.getLength(); i < stape; ++i){
            Node nNode = list.item(i);
            if(nNode == null)
                continue;

            String nodeName = nNode.getNodeName();
            if(nodeName == null)
                continue;

            if(nodeName.equals(name))
                return nNode;
        }

        return null;
    }

    ////////////////////////////////////////////////////////////////////////
    // members
    private static final String m_SystemTag             = "system";
    private static final String m_NameAtrr              = "name";
    private static final String m_ProperiesTag          = "properties";
    private static final String m_GunTag                = "gun";
    private static final String m_BulletTag             = "bullet";
    private static final String m_CalTag                = "cal";
    private static final String m_SpeedTag              = "speed";
    private static final String m_WeightTag             = "weight";
    private static final String m_DescriptionTag        = "description";
    private static final String m_ValAtrr               = "val";
    private static final String m_PriceDivisionTag      = "price-division";
    private static final String m_priceClickTag         = "price-click";
    private static final String m_SightTag              = "sight";
    private static final String m_NormalTag             = "normal";
    private static final String m_TargetAngleTag        = "target-angle";
    private static final String m_LateralTiltTag        = "lateral-tilt";
    private static final String m_PressureTag           = "pressure";
    private static final String m_AirTemperatureTag     = "air-temperature";
    private static final String m_RelativeHumidityTag   = "relative-humidity";
    private static final String m_OverestimationTblTag  = "overestimation-tbl";
    private static final String m_NumAttr               = "num";
    private static final String m_DistTag               = "dist";
    private static final String m_OverestimationAtrr    = "overestimation";
    private static final String m_CorrectionTblTag      = "correction-tbl";
    private static final String m_DistCorrTag           = "dist-corr";
    private static final String m_HeightCorrTag         = "height-corr";
    private static final String m_TempTag               = "temp";
    private static final String m_SpeedAtrr             = "speed";
    private static final String m_WindTag               = "wind";
    private static final String m_DeviationAtrr         = "deviation";
    private static final String m_DirectionCorrTag      = "direction-corr";
    private static final String m_DerivationTag         = "derivation";


    private String              m_SystemName;
    private GunSystemHolder     m_Gs;

    private GunsVec             m_Guns;
    private Bullet              m_BulletProp;
    private Sight               m_SightProp;
    private Normal              m_NormalConditions;

    private OverestimationVec   m_OverestimationTbl;
    private CorrectionVec       m_CorrectionsTbl;
}
