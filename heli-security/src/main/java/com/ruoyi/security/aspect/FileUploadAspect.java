package com.ruoyi.security.aspect;

import java.util.Date;
import java.util.Map;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.ruoyi.security.service.*;
import com.ruoyi.security.service.impl.SecurityRequireExpectPartyServiceImpl;
import jakarta.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.security.domain.SecurityFileManagement;
import com.ruoyi.security.utils.ThreadLocalContext;
import com.ruoyi.security.service.ISecurityRiskOpportunityAssessmentService;


/**
 * 文件上传切面，专门用于拦截 security-conm 模块下的文件上传/导入方法
 *
 * 作用范围：
 * 1. 只拦截 com.ruoyi.security.controller 包下的 Controller
 * 2. 只处理包含 upload、import、file 等关键词的方法
 * 3. 只处理 URI 包含 "/security/" 的请求
 * 4. 自动记录文件上传信息到 security_file_management 表
 * 5. 自动更新相关业务表的 related_id 字段
 */
@Aspect
@Component
public class FileUploadAspect {
    private static final Logger log = LoggerFactory.getLogger(FileUploadAspect.class);

    // 用于标记请求是否已处理文件的属性名
    private static final String FILE_PROCESSED_FLAG = "FILE_PROCESSED_FLAG";
    private static final String FILE_MANAGEMENT_ID = "FILE_MANAGEMENT_ID";

    @Autowired
    private ISecurityFileManagementService fileManagementService;
    
    @Autowired
    private ISecuritySysMenuService securitySysMenuService;
    @Autowired
    private ISecurityEnvironmentalOrganizationDescriptionService environmentalService;
    @Autowired
    private ISecurityRequireExpectPartyService requireExpectPartyService;
    @Autowired
    private ISecurityRiskOpportunityAssessmentService riskAssessmentService;
    @Autowired
    private ISecurityCompanyKeyWorksService companyKeyWorksService;
    @Autowired
    private ISecueityCompanyKpiService secueityCompanyKpiService;
    @Autowired
    private IAnnualEnvironmentalOccupationalHealthAndSafetyGoalsService annualEnvironmentalOccupationalHealthAndSafetyGoalsService;
    @Autowired
    private ISecurityCompanyImportantEnvironmentalFactorsService importantFactorsService;
    @Autowired
    private ISecutityEnvironmentalFactorsImpactService impactService;
    @Autowired
    private ISecurityHazardSourceListService hazardSourceListService;
    @Autowired
    private ISecurityUnacceptableRiskListService unacceptableRiskListService;
    @Autowired
    private ISecutityLegalRegulationsIdentificationList1Service regulationsIdentificationList1Service;
    @Autowired
    private ISecutityLegalRegulationsIdentificationListService regulationsIdentificationListService;
    @Autowired
    private ISecurityComplianceEvaluationRecordsService securityComplianceEvaluationRecordsService;
    @Autowired
    private ISecurityEducationAnnualTrainingPlanService securityEducationAnnualTrainingPlanService;
    @Autowired
    private ISecurityEnvironmentalOhsManagementSystemDocumentsService securityEnvironmentalOhsManagementSystemDocumentsService;
    @Autowired
    private ISecurityControlledDocumentDistributionDirectoryService securityControlledDocumentDistributionDirectoryService;
    @Autowired
    private ISecurityObsoleteControlledDocumentDisposalRegisterService securityObsoleteControlledDocumentDisposalRegisterService;
    @Autowired
    private ISecurityHazardousChemicalInventoryService securityHazardousChemicalInventoryService;
    @Autowired
    private ISecurityHazardousMaterialsSafetyInspectionService securityHazardousMaterialsSafetyInspectionService;
    @Autowired
    private ISecurityHazardousMaterialsHandlingLedgerService securityHazardousMaterialsHandlingLedgerService;
    @Autowired
    private ISecurityOccupationalHazardPositionPersonnelListService securityOccupationalHazardPositionPersonnelListService;
    @Autowired
    private ISecurityHazardPointLedgerService securityHazardPointLedgerService;
    @Autowired
    private ISecuritySpecialOperationsPersonnelInfoService securitySpecialOperationsPersonnelInfoService;
    @Autowired
    private ISecurityRelatedPartyLedgerService securityRelatedPartyLedgerService;
    @Autowired
    private ISecuritySpecialOperationsCertificateLedgerService securitySpecialOperationsCertificateLedgerService;
    @Autowired
    private ISecurityOccupationalHealthProtectiveEquipmentLedgerService securityOccupationalHealthProtectiveEquipmentLedgerService;
    @Autowired
    private ISecurityEmergencyDrillPlanService securityEmergencyDrillPlanService;
    @Autowired
    private ISecurityEmergencySuppliesLedgerService securityEmergencySuppliesLedgerService;
    @Autowired
    private ISecurityEnvironmentalOhsRecordListService securityEnvironmentalOhsRecordListService;
    @Autowired
    private ISecuritySafetyProductionResponsibilityListService securitySafetyProductionResponsibilityListService;
    @Autowired
    private ISecurityThreeEducatiomService securityThreeEducatiomService;
    @Autowired
    private ISecurityKnowledgeAssessmentService securityKnowledgeAssessmentService;
    @Autowired
    private ISecurityEquipmentSafetyFacilityLedgerService securityEquipmentSafetyFacilityLedgerService;
    @Autowired
    private ISecurityPeopleListService securityPeopleListService;
    @Autowired
    private ISecurityDangerWangListService securityDangerWangListService;

    /**
     * 定义切点 - 只拦截security模块下包含upload、import、file等关键词的Controller方法
     */
    @Pointcut("(" +
              "execution(* com.ruoyi.security.controller..*.*upload*(..)) || " +
              "execution(* com.ruoyi.security.controller..*.*import*(..)) || " +
              "execution(* com.ruoyi.security.controller..*.*Upload*(..)) || " +
              "execution(* com.ruoyi.security.controller..*.*Import*(..)) || " +
              "execution(* com.ruoyi.security.controller..*.*file*(..)) || " +
              "execution(* com.ruoyi.security.controller..*.*File*(..)))")
    public void uploadPointcut() {}
    
    /**
     * 定义切点 - 只拦截security模块下的表单管理相关的Controller方法
     */
    @Pointcut("execution(* com.ruoyi.security.controller..*.*(..))")
    public void formManagementPointcut() {}

    /**
     * 在上传方法执行前记录信息
     */
    @Before("uploadPointcut()")
    public void beforeUpload(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();

        // 额外安全检查：确保只处理security模块的Controller
        if (!className.contains("com.ruoyi.security.controller")) {
            log.debug("跳过非security模块的Controller: {}", className);
            return;
        }

        // 进一步检查请求URI，确保是security模块的请求
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String uri = request.getRequestURI();
                if (!uri.contains("/security/")) {
                    log.debug("跳过非security模块的URI请求: {}", uri);
                    return;
                }
            }
        } catch (Exception e) {
            log.debug("无法获取请求URI，跳过检查");
            return;
        }

        log.info("准备处理security模块上传/导入方法: {}.{}",
                className, joinPoint.getSignature().getName());
    }

    /**
            * 环绕通知，只处理security模块下的导入方法
 */
    @Around("execution(* com.ruoyi.security.controller..*.*import*(..))")
    public Object aroundImportMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(FileUploadAspect.class);

        // 额外安全检查：确保只处理security-conm模块的Controller
        String className = joinPoint.getTarget().getClass().getName();
        if (!className.contains("com.ruoyi.security.controller")) {
            log.debug("跳过非security模块的Controller: {}", className);
            return joinPoint.proceed();
        }

        // 进一步检查请求URI，确保是security模块的请求
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String uri = request.getRequestURI();
            if (!uri.contains("/security/")) {
                log.debug("跳过非security模块的URI请求: {}", uri);
                return joinPoint.proceed();
            }
        }

        log.info("环绕通知：处理security模块导入方法 {}.{}",
                className, joinPoint.getSignature().getName());

                    // 获取当前请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed();
        }

                        HttpServletRequest request = attributes.getRequest();
        
        // 获取业务ID（从ThreadLocal中）
        Long threadLocalRelatedId = ThreadLocalContext.getRelatedId();
        
        // 获取Referer和当前URI
        String referer = request.getHeader("Referer");
                        String uri = request.getRequestURI();
        
        // 构造relatedUrl，优先使用Referer
        String relatedUrl = StringUtils.isNotEmpty(referer) ? referer : uri;
        relatedUrl = ensureSecurityConmPrefix(relatedUrl);

        // 获取当前用户
                        String username = SecurityUtils.getUsername();

                        // 获取模块名称
        String moduleName = getModuleNameFromUri(referer);
                        if ("未知模块".equals(moduleName)) {
                            moduleName = getModuleNameFromUri(uri);
                        }

        Long fileManagementId = null;

        // 处理文件上传
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) arg;
                if (!file.isEmpty()) {
                    // 处理文件上传，获取文件管理记录ID
                    fileManagementId = processMultipartFile(
                        file,
                        moduleName,
                                getShortClassName(joinPoint.getTarget().getClass().getSimpleName()),
                                joinPoint.getSignature().getName(),
                        username,
                        "IMPORT",
                        relatedUrl,
                        threadLocalRelatedId
                    );

                        if (fileManagementId != null) {
                        // 将处理标志和文件管理记录ID存储到请求属性中
                        request.setAttribute(FILE_PROCESSED_FLAG, true);
                        request.setAttribute(FILE_MANAGEMENT_ID, fileManagementId);
                        
                        // 存储到ThreadLocal中
                            ThreadLocalContext.setRelatedId(fileManagementId);
                        log.info("已将文件管理记录ID: {} 存储到ThreadLocal和请求属性中", fileManagementId);
                    }
                    break;
                }
            }
        }

        // 执行原始方法
        Object result = joinPoint.proceed();

        // 如果有文件管理记录ID，更新关联ID
        if (fileManagementId != null) {
            String controllerClassName = joinPoint.getTarget().getClass().getSimpleName();

            // 处理不同的返回类型
            boolean shouldUpdateRelatedId = false;

            if (result instanceof AjaxResult) {
                AjaxResult ajaxResult = (AjaxResult) result;
                shouldUpdateRelatedId = ajaxResult.isSuccess();
            } else if (result == null || result instanceof Void) {
                // 对于void返回类型，如果没有抛出异常，认为执行成功
                shouldUpdateRelatedId = true;
            } else {
                // 对于其他返回类型，也认为执行成功
                shouldUpdateRelatedId = true;
            }

            if (shouldUpdateRelatedId) {
                log.info("导入成功，准备更新关联ID: {}，控制器类名: {}", fileManagementId, controllerClassName);
                updateRelatedIdForSecurityController(controllerClassName, fileManagementId);
            }
        }

        return result;
    }
    
    /**
     * 根据控制器类型更新关联 ID
     */
    private void updateRelatedIdForSecurityController(String controllerName, Long fileManagementId) {
        int updatedRows = 0;

        try {
            // 识别所有 security 控制器并更新关联 ID
            if (controllerName.contains("EnvironmentalOrganization")) {
                // 环境识别导入
                updatedRows = environmentalService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新环境识别数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("RequireExpectParty")) {
                // 相关方期望
                updatedRows = requireExpectPartyService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新相关方期望数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            }
                else if (controllerName.contains("RiskOpportunityAssessment")){
                updatedRows = riskAssessmentService.updateLatestImportedRelatedId(fileManagementId);
               //公司更新重点工作
            } else if (controllerName.contains("CompanyKeyWorksController")) {
                updatedRows = companyKeyWorksService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新重点工作ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecueityCompanyKpiController")) {
                // 公司KPI
                updatedRows = secueityCompanyKpiService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新公司KPI数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityAccidentCauseAnalysis")) {
                // 事故原因分析secueityCompanyKpiService
                updatedRows = secueityCompanyKpiService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新事故原因分析数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("AnnualEnvironmentalOccupationalHealthAndSafetyGoals") || controllerName.contains("HealthAndSafetyGoals")) {
                // 年度环境职业健康安全目标
                updatedRows = annualEnvironmentalOccupationalHealthAndSafetyGoalsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新年度环境职业健康安全目标数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityCompanyImportantEnvironmentalFactors")) {
                // 重要环境因素
                updatedRows = importantFactorsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新重要环境因素数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecutityEnvironmentalFactorsImpact") || controllerName.contains("SecurityImpact")) {
                // 环境因素清单
                updatedRows = impactService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新环境因素清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardSourceList") || controllerName.contains("SecurityRisklist")) {
                // 危险源清单
                updatedRows = hazardSourceListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新危险源清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityUnacceptableRiskList") || controllerName.contains("Unacceptablerisklist")) {
                // 不可接受风险清单
                updatedRows = unacceptableRiskListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新不可接受风险清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecutityLegalRegulationsIdentificationList1") || controllerName.contains("RegulationsIdentificationList1")) {
                // 安全法律法规识别清单
                updatedRows = regulationsIdentificationList1Service.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全法律法规识别清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if(controllerName.contains("SecutityLegalRegulationsIdentificationList")|| controllerName.contains("RegulationsIdentificationList")) {
                // 安全法律法规识别清单
                updatedRows = regulationsIdentificationListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全法律法规识别清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityComplianceEvaluationRecords") || controllerName.contains("Compliance")) {
                // 合规性评价记录
                updatedRows = securityComplianceEvaluationRecordsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新合规性评价记录数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEducationAnnualTrainingPlan") || controllerName.contains("SecurityTrainingplan")) {
                // 年度培训计划
                updatedRows = securityEducationAnnualTrainingPlanService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新年度培训计划数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            }
               else if (controllerName.contains("SecurityHazardousChemicalInventory") || controllerName.contains("HazardousChemicalInventory")){
                // 危险化学品台账
                updatedRows = securityHazardousChemicalInventoryService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新危险化学品台账的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardousMaterialsSafetyInspection") || controllerName.contains("HazardousMaterialsSafetyInspection") || controllerName.contains("hazardousinspection")){
                // 危化品检查记录
                updatedRows = securityHazardousMaterialsSafetyInspectionService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新危化品检查记录的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardousMaterialsHandlingLedger") || controllerName.contains("HazardousMaterialsHandlingLedger") || controllerName.contains("hazardousledger")){
                // 危化品处理台账
                updatedRows = securityHazardousMaterialsHandlingLedgerService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新危化品处理台账的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityOccupationalHazardPositionPersonnelList") || controllerName.contains("OccupationaPersonnelList")){
                // 危险职业岗位人员清单及管理台帐
                updatedRows = securityOccupationalHazardPositionPersonnelListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新危险职业岗位人员清单及管理台帐的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardPointLedger") || controllerName.contains("HazardPointLedger") || controllerName.contains("hazardpointledger")){
                // 有害台账
                updatedRows = securityHazardPointLedgerService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新有害台账的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecuritySpecialOperationsPersonnelInfo") || controllerName.contains("SpecialOperationsPersonnelInfo") || controllerName.contains("specialoperationspersonnelInfo")){
                // 特种作业人员信息
                updatedRows = securitySpecialOperationsPersonnelInfoService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新特种作业人员信息的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecuritySpecialOperationsCertificateLedger") || controllerName.contains("SpecialOperationsCertificate") || controllerName.contains("specialoperationscertificate")) {
                // 特种设备台账
                updatedRows = securitySpecialOperationsCertificateLedgerService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新特种设备台账的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityOccupationalHealthProtectiveEquipmentLedger") || controllerName.contains("OccupationalHealthProtectiveEquipment") || controllerName.contains("healthprotectiveequipment")) {
                // 职业健康防护用品台帐
                updatedRows = securityOccupationalHealthProtectiveEquipmentLedgerService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新职业健康防护用品台帐的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEmergencyDrillPlan") || controllerName.contains("EmergencyDrillPlan") || controllerName.contains("emergencydrillplan")) {
                // 应急演练计划
                updatedRows = securityEmergencyDrillPlanService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新应急演练计划的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEmergencySuppliesLedger") || controllerName.contains("EmergencySuppliesLedger") || controllerName.contains("emergencysuppliesledger")) {
                // 应急物资管理台帐
                updatedRows = securityEmergencySuppliesLedgerService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新应急物资管理台帐的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEnvironmentalOhsRecordList") || controllerName.contains("EnvironmentalOhsRecordList") || controllerName.contains("environmentalohsrecordlist")) {
                // 环境职业健康安全记录清单
                updatedRows = securityEnvironmentalOhsRecordListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新环境职业健康安全记录清单的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecuritySafetyProductionResponsibilityList") || controllerName.contains("ProductionResponsibilityList") || controllerName.contains("productionresponsibilitylist")) {
                // 安全生产责任制清单
                updatedRows = securitySafetyProductionResponsibilityListService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全生产责任制清单的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            }  else if (controllerName.contains("SecurityRelatedPartyLedger") || controllerName.contains("RelatedPartyLedger") || controllerName.contains("relatedpartyledger")) {
                   updatedRows = securityRelatedPartyLedgerService.updateLatestImportedRelatedId(fileManagementId);
                   log.info("已更新相关方台账的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityThreeEducatiom") || controllerName.contains("ThreeEducatiom")) {
                   updatedRows = securityThreeEducatiomService.updateLatestImportedRelatedId(fileManagementId);
                   log.info("已更新新职工三级安全教育台账的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityKnowledgeAssessment") || controllerName.contains("knowledgeassessment")) {
                   updatedRows = securityKnowledgeAssessmentService.updateLatestImportedRelatedId(fileManagementId);
                   log.info("已更新安全知识考核的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEquipmentSafetyFacilityLedger") || controllerName.contains("SafetyFacilityLedger")) {
                   updatedRows = securityEquipmentSafetyFacilityLedgerService.updateLatestImportedRelatedId(fileManagementId);
                   log.info("已更新安全防护设备设施的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityPeopleList") || controllerName.contains("PeopleList")) {
                   updatedRows = securityPeopleListService.updateLatestImportedRelatedId(fileManagementId);
                   log.info("已更新班组人员清单的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityDangerWangList") || controllerName.contains("DangerWangList")) {
                   updatedRows = securityDangerWangListService.updateLatestImportedRelatedId(fileManagementId);
                   log.info("已更新风险网格化清单的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            }
                // 事故措施跟踪
                else if (controllerName.contains("SecurityAccidentMeasuresTracking")) {
                updatedRows = getServiceAndUpdateRelatedId("accidentMeasuresTrackingService", fileManagementId);
                log.info("已更新事故措施跟踪数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityAnnualPlan")) {
                // 年度计划
                updatedRows = getServiceAndUpdateRelatedId("annualPlanService", fileManagementId);
                log.info("已更新年度计划数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityChangeManagement")) {
                // 变更管理
                updatedRows = getServiceAndUpdateRelatedId("changeManagementService", fileManagementId);
                log.info("已更新变更管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityCheckReport")) {
                // 检查报告
                updatedRows = getServiceAndUpdateRelatedId("checkReportService", fileManagementId);
                log.info("已更新检查报告数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityCollaboration")) {
                // 协作管理
                updatedRows = getServiceAndUpdateRelatedId("collaborationService", fileManagementId);
                log.info("已更新协作管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEmergencyDrill")) {
                // 应急演练
                updatedRows = getServiceAndUpdateRelatedId("emergencyDrillService", fileManagementId);
                log.info("已更新应急演练数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEmergencyPlan")) {
                // 应急预案
                updatedRows = getServiceAndUpdateRelatedId("emergencyPlanService", fileManagementId);
                log.info("已更新应急预案数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardousInspection")) {
                // 危险源检查
                updatedRows = getServiceAndUpdateRelatedId("hazardousInspectionService", fileManagementId);
                log.info("已更新危险源检查数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityHazardousManagement")) {
                // 危险源管理
                updatedRows = getServiceAndUpdateRelatedId("hazardousManagementService", fileManagementId);
                log.info("已更新危险源管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityInternalAudit")) {
                // 内部审核
                updatedRows = getServiceAndUpdateRelatedId("internalAuditService", fileManagementId);
                log.info("已更新内部审核数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityKpiEvaluation")) {
                // KPI评价
                updatedRows = getServiceAndUpdateRelatedId("kpiEvaluationService", fileManagementId);
                log.info("已更新KPI评价数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityLegalRequirement")) {
                // 法律法规
                updatedRows = getServiceAndUpdateRelatedId("legalRequirementService", fileManagementId);
                log.info("已更新法律法规数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityManagementReview")) {
                // 管理评审
                updatedRows = getServiceAndUpdateRelatedId("managementReviewService", fileManagementId);
                log.info("已更新管理评审数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityObjective")) {
                // 目标指标
                updatedRows = getServiceAndUpdateRelatedId("objectiveService", fileManagementId);
                log.info("已更新目标指标数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityRiskAssessment")) {
                // 风险评估
                updatedRows = getServiceAndUpdateRelatedId("riskAssessmentService", fileManagementId);
                log.info("已更新风险评估数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityTraining")) {
                // 培训管理
                updatedRows = getServiceAndUpdateRelatedId("trainingService", fileManagementId);
                log.info("已更新培训管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityFileManagement")) {
                // 文件管理
                updatedRows = getServiceAndUpdateRelatedId("fileManagementService", fileManagementId);
                log.info("已更新文件管理数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityLegalRegulationsIdentificationList1")) {
                // 安全法律法规识别清单
                updatedRows = regulationsIdentificationList1Service.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新安全法律法规识别清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityEnvironmentalOhsManagementSystemDocuments")) {
                // 环境职业健康安全管理体系文件清单
                updatedRows = securityEnvironmentalOhsManagementSystemDocumentsService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新环境职业健康安全管理体系文件清单数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityControlledDocumentDistributionDirectory") || controllerName.contains("Controlleddirectory")) {
                // 受控文件发放目录
                updatedRows = securityControlledDocumentDistributionDirectoryService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新受控文件发放目录数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else if (controllerName.contains("SecurityObsoleteControlledDocumentDisposalRegister") || controllerName.contains("Obsoleteregister")) {
                // 作废受控文件收回销毁登记
                updatedRows = securityObsoleteControlledDocumentDisposalRegisterService.updateLatestImportedRelatedId(fileManagementId);
                log.info("已更新作废受控文件收回销毁登记数据的关联 ID: {}, 更新行数: {}", fileManagementId, updatedRows);
            } else {
                // 其他 security 控制器，通用处理
                log.info("未找到匹配的服务类，尝试通用处理: {}", controllerName);

                // 尝试从控制器名称推断服务名称
                String serviceName = inferServiceNameFromController(controllerName);
                if (serviceName != null) {
                    updatedRows = getServiceAndUpdateRelatedId(serviceName, fileManagementId);
                    log.info("已更新 {} 数据的关联 ID: {}, 更新行数: {}", serviceName, fileManagementId, updatedRows);
                } else {
                    log.warn("无法处理控制器 {}, 未能更新关联 ID", controllerName);
                }
            }
        } catch (Exception e) {
            log.error("更新关联 ID 失败: {}", e.getMessage(), e);
        }
    }

    /**
     * 从控制器名称推断服务名称
     */
    private String inferServiceNameFromController(String controllerName) {
        // 移除 "Controller" 后缀
        String name = controllerName.replace("Controller", "");

        // 首字母小写
        if (name.length() > 0) {
            name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        }

        // 添加 "Service" 后缀
        name += "Service";

        return name;
    }

    /**
     * 获取服务并更新关联 ID
     */
    private int getServiceAndUpdateRelatedId(String serviceName, Long fileManagementId) {
        try {
            // 尝试从 Spring 容器中获取服务
            Object service = SpringUtils.getBean(serviceName);
            if (service != null) {
                // 尝试调用 updateLatestImportedRelatedId 方法
                Method method = service.getClass().getMethod("updateLatestImportedRelatedId", Long.class);
                Object result = method.invoke(service, fileManagementId);
                if (result instanceof Integer) {
                    return (Integer) result;
                }
            }
        } catch (Exception e) {
            log.error("获取服务或调用方法失败: {}", e.getMessage());
        }
        return 0;
    }

@Order(Integer.MAX_VALUE)
@AfterReturning("formManagementPointcut()")
public void afterFormManagement() {
    // 清除ThreadLocal中的数据，防止内存泄漏
    ThreadLocalContext.clearRelatedId();
    log.debug("已清除ThreadLocal中的relatedId");
}
    
    /**
     * 在上传方法返回后执行
     */
    @AfterReturning(pointcut = "uploadPointcut()", returning = "result")
    public void afterUpload(JoinPoint joinPoint, Object result) {
        String className = joinPoint.getTarget().getClass().getName();

        // 额外安全检查：确保只处理security模块的Controller
        if (!className.contains("com.ruoyi.security.controller")) {
            log.debug("跳过非security模块的Controller: {}", className);
            return;
        }

        // 进一步检查请求URI，确保是security模块的请求
        ServletRequestAttributes attributes = null;
        try {
            attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String uri = request.getRequestURI();
                if (!uri.contains("/security/")) {
                    log.debug("跳过非security模块的URI请求: {}", uri);
                    return;
                }
            }
        } catch (Exception e) {
            log.debug("无法获取请求URI，跳过检查");
            return;
        }

        log.info("拦截到security模块上传/导入方法: {}.{}",
                className, joinPoint.getSignature().getName());

        try {
            // 获取当前请求
            if (attributes == null) {
                attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            }
            if (attributes == null) {
                log.warn("无法获取请求上下文，可能不在Web请求中");
                return;
            }
            
            HttpServletRequest request = attributes.getRequest();
            
            // 检查是否已经处理过文件
            Boolean fileProcessed = (Boolean) request.getAttribute(FILE_PROCESSED_FLAG);
            if (Boolean.TRUE.equals(fileProcessed)) {
                log.info("文件已在环绕通知中处理，跳过afterUpload处理");
                return;
            }
            
            // 获取当前ThreadLocal中的relatedId
            Long threadLocalRelatedId = ThreadLocalContext.getRelatedId();
            
            // 获取当前登录用户
            String username = SecurityUtils.getLoginUser().getUser().getUserName();
            
            // 获取请求的URI和Referer
            String uri = request.getRequestURI();
            String referer = request.getHeader("Referer");
            
            // 构造完整的relatedUrl
            String relatedUrl = StringUtils.isNotEmpty(referer) ? referer : uri;
            relatedUrl = ensureSecurityConmPrefix(relatedUrl);
            
            // 获取模块名称
            String moduleName = getModuleNameFromUri(referer);
            if ("未知模块".equals(moduleName)) {
                moduleName = getModuleNameFromUri(uri);
            }
            
            String controllerClassName = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            
            // 处理文件上传
            boolean fileHandled = false;
            
            // 处理方法参数中的MultipartFile
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof MultipartFile) {
                    MultipartFile file = (MultipartFile) arg;
                    if (!file.isEmpty()) {
                        processMultipartFile(file, moduleName, getShortClassName(controllerClassName),
                                methodName, username, "UPLOAD", relatedUrl, threadLocalRelatedId);
                        fileHandled = true;
                    }
                } else if (arg instanceof MultipartFile[]) {
                    MultipartFile[] files = (MultipartFile[]) arg;
                    for (MultipartFile file : files) {
                        if (!file.isEmpty()) {
                            processMultipartFile(file, moduleName, getShortClassName(controllerClassName),
                                    methodName, username, "UPLOAD", relatedUrl, threadLocalRelatedId);
                            fileHandled = true;
                                    }
                    }
                }
            }
            
            // 如果没有处理过文件，检查MultipartHttpServletRequest
            if (!fileHandled && request instanceof MultipartHttpServletRequest) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
                
                for (MultipartFile file : fileMap.values()) {
                    if (!file.isEmpty()) {
                        processMultipartFile(file, moduleName, getShortClassName(controllerClassName),
                                methodName, username, "UPLOAD", relatedUrl, threadLocalRelatedId);
                        fileHandled = true;
                    }
                }
            }
                        } catch (Exception e) {
            log.error("处理文件上传失败", e);
        }
    }
    
    /**
     * 处理上传的文件，返回文件管理记录ID
     */
    private Long processMultipartFile(MultipartFile file, String moduleName, String moduleCode,
            String methodName, String username, String fileCategory, String relatedUrl, Long threadLocalRelatedId) {
        if (file == null || file.isEmpty()) {
            return null;
        }

            String originalFilename = file.getOriginalFilename();
        if (StringUtils.isEmpty(originalFilename)) {
            return null;
        }

                try {
                    SecurityFileManagement fileManagement = new SecurityFileManagement();
                    fileManagement.setFileName(originalFilename);
            fileManagement.setFilePath("securityConm/" + originalFilename);
                    fileManagement.setFileSize(file.getSize());
                    fileManagement.setFileType(file.getContentType());
                    fileManagement.setFileCategory(fileCategory);
                    fileManagement.setModuleName(moduleName);
                    fileManagement.setModuleCode(moduleCode);
                    fileManagement.setUploadTime(new Date());
                    fileManagement.setUploadUser(username);
                    fileManagement.setStatus("0");
                    fileManagement.setRemark("系统自动记录-" + methodName);
            fileManagement.setRelatedUrl(relatedUrl);

            // 设置关联ID
                    if (threadLocalRelatedId != null) {
                        fileManagement.setRelatedId(threadLocalRelatedId);
                        log.info("使用ThreadLocal中的关联ID: {}", threadLocalRelatedId);
            }

                    int rows = fileManagementService.insertSecurityFileManagement(fileManagement);
            if (rows > 0) {
                log.info("成功记录文件: {}, ID: {}, 关联ID: {}", originalFilename, fileManagement.getId(), fileManagement.getRelatedId());
                return fileManagement.getId();
                    }
                } catch (Exception e) {
            log.error("记录文件信息失败: {}", e.getMessage(), e);
        }
        return null;
    }
    
    /**
     * 标准化URL，确保是相对路径
     * 
     * @param url 原始URL
     * @return 标准化后的相对URL
     */
    private String normalizeUrl(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        
        String normalizedUrl = url;
        
        // 处理URL中的查询参数，保留最后出现的参数值（最新的参数）
        if (normalizedUrl.contains("?")) {
            String baseUrl = normalizedUrl.substring(0, normalizedUrl.indexOf("?"));
            String queryString = normalizedUrl.substring(normalizedUrl.indexOf("?") + 1);
            
            // 解析查询参数，保留最后一次出现的参数（最新的参数）
            Map<String, String> uniqueParams = new java.util.LinkedHashMap<>();
            String[] params = queryString.split("&");
            for (String param : params) {
                if (param.contains("=")) {
                    String[] keyValue = param.split("=", 2);
                    String key = keyValue[0];
                    String value = keyValue.length > 1 ? keyValue[1] : "";
                    
                    // 始终使用最后一次出现的参数值（覆盖旧值）
                    uniqueParams.put(key, value);
                }
            }
            
            // 重建查询字符串
            StringBuilder newQueryString = new StringBuilder();
            for (Map.Entry<String, String> entry : uniqueParams.entrySet()) {
                if (newQueryString.length() > 0) {
                    newQueryString.append("&");
                }
                newQueryString.append(entry.getKey()).append("=").append(entry.getValue());
            }
            
            // 重建URL
            normalizedUrl = baseUrl;
            if (newQueryString.length() > 0) {
                normalizedUrl += "?" + newQueryString.toString();
            }
            
            log.debug("处理重复参数后的URL（保留最新参数）: {}", normalizedUrl);
        }
        
        // 处理重复的URL前缀问题
        String httpPattern = "http://";
        String httpsPattern = "https://";
        int lastHttpIndex = normalizedUrl.lastIndexOf(httpPattern);
        int lastHttpsIndex = normalizedUrl.lastIndexOf(httpsPattern);
        int lastIndex = Math.max(lastHttpIndex, lastHttpsIndex);
        
        if (lastIndex > 0) {
            normalizedUrl = normalizedUrl.substring(lastIndex);
        }
        
        // 移除域名和端口号部分
        if (normalizedUrl.startsWith(httpPattern) || normalizedUrl.startsWith(httpsPattern)) {
            int pathStartIndex = normalizedUrl.indexOf('/', 8); // 跳过http(s)://
            if (pathStartIndex > 0) {
                normalizedUrl = normalizedUrl.substring(pathStartIndex);
            }
        }
        
        // 先移除所有securityConm前缀，但只处理以securityConm/开头的情况
        while (normalizedUrl.startsWith("/securityConm/") || normalizedUrl.startsWith("securityConm/")) {
            if (normalizedUrl.startsWith("/securityConm/")) {
                normalizedUrl = "/" + normalizedUrl.substring("/securityConm/".length());
            } else if (normalizedUrl.startsWith("securityConm/")) {
                normalizedUrl = normalizedUrl.substring("securityConm/".length());
            }
        }
        
        // 提取security1或security2后面的部分
        int securityIndex1 = normalizedUrl.indexOf("security1");
        int securityIndex2 = normalizedUrl.indexOf("security2");
        
        if (securityIndex1 != -1) {
            normalizedUrl = normalizedUrl.substring(securityIndex1);
        } else if (securityIndex2 != -1) {
            normalizedUrl = normalizedUrl.substring(securityIndex2);
        } else {
            // 如果没有找到security1或security2，尝试查找security
            int securityIndex = normalizedUrl.indexOf("security");
            if (securityIndex != -1) {
                normalizedUrl = normalizedUrl.substring(securityIndex);
            }
        }
        
        // 确保URL以/开头
        if (!normalizedUrl.startsWith("/") && !normalizedUrl.startsWith("#")) {
            normalizedUrl = "/" + normalizedUrl;
        }
        
        return normalizedUrl;
    }
    
    /**
     * 从URI中获取模块名称
     */
    private String getModuleNameFromUri(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "未知模块";
        }
        
        try {
            log.debug("开始从URI解析模块名称: {}", uri);
            
            // 先使用normalizeUrl处理URL，确保处理了重复参数
            uri = normalizeUrl(uri);
            log.debug("标准化后的URI: {}", uri);
            
            // 处理URL中包含问号的情况，截取问号前的部分
            if (uri.contains("?")) {
                uri = uri.substring(0, uri.indexOf("?"));
                log.debug("URL包含问号，截取后的URI: {}", uri);
            }
            
            // 解析URI获取模块标识
            String moduleKey = "";
            StringBuilder modulePath = new StringBuilder();
            
            // 处理前端URL格式（例如：http://localhost:8080/#/security/fileManagement）
            if (uri.contains("#/")) {
                String path = uri.substring(uri.indexOf("#/") + 2);
                log.debug("从前端URL提取路径: {}", path);
                
                String[] pathParts = path.split("/");
                
                // 查找 security 或 security1 或 security2
                int securityIndex = -1;
                for (int i = 0; i < pathParts.length; i++) {
                    if (pathParts[i] != null && !pathParts[i].isEmpty() && 
                        (pathParts[i].equals("security") || 
                         pathParts[i].equals("security1") || 
                         pathParts[i].equals("security2"))) {
                        securityIndex = i;
                        break;
                    }
                }
                
                // 如果找到了 security，提取它和它后面的所有部分
                if (securityIndex >= 0) {
                    modulePath.append(pathParts[securityIndex]);
                    // 添加 security 后面的所有部分
                    for (int i = securityIndex + 1; i < pathParts.length; i++) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            modulePath.append("/").append(pathParts[i]);
                        }
                    }
                    moduleKey = modulePath.toString();
                    log.debug("从前端URL提取到完整模块路径: {}", moduleKey);
                } else {
                    // 如果没有找到 security，使用最后一个非空部分
                    for (int i = pathParts.length - 1; i >= 0; i--) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            moduleKey = pathParts[i];
                            log.debug("从前端URL提取到模块标识: {}", moduleKey);
                            break;
                        }
                    }
                }
            } else {
                // 处理后端API URL格式（例如：/security/environmentidicaation/import）
                String[] pathParts = uri.split("/");
                
                // 查找 security 或 security1 或 security2
                int securityIndex = -1;
                for (int i = 0; i < pathParts.length; i++) {
                    if (pathParts[i] != null && !pathParts[i].isEmpty() && 
                        (pathParts[i].equals("security") || 
                         pathParts[i].equals("security1") || 
                         pathParts[i].equals("security2"))) {
                        securityIndex = i;
                        break;
                    }
                }
                
                // 如果找到了 security，提取它和它后面的所有部分
                if (securityIndex >= 0) {
                    modulePath.append(pathParts[securityIndex]);
                    // 添加 security 后面的所有部分
                    for (int i = securityIndex + 1; i < pathParts.length; i++) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            modulePath.append("/").append(pathParts[i]);
                        }
                    }
                    moduleKey = modulePath.toString();
                    log.debug("从后端URL提取到完整模块路径: {}", moduleKey);
                } else {
                    // 如果没有找到 security，使用最后一个非空部分
                    for (int i = pathParts.length - 1; i >= 0; i--) {
                        if (pathParts[i] != null && !pathParts[i].isEmpty()) {
                            moduleKey = pathParts[i];
                            log.debug("从后端URL提取到模块标识: {}", moduleKey);
                            break;
                        }
                    }
                }
            }
            
            // 处理特殊情况：如果模块标识是一些常见的操作名称，则继续向前查找
            if (moduleKey.equals("import") || moduleKey.equals("upload") || moduleKey.equals("download") || 
                moduleKey.equals("list") || moduleKey.equals("add") || moduleKey.equals("edit") || 
                moduleKey.equals("delete") || moduleKey.equals("export")) {
                
                log.debug("检测到操作名称，继续向前查找模块标识: {}", moduleKey);
                
                // 重新从URL中提取模块标识
                String[] parts;
                if (uri.contains("#/")) {
                    String path = uri.substring(uri.indexOf("#/") + 2);
                    parts = path.split("/");
                } else {
                    parts = uri.split("/");
                }
                
                // 查找倒数第二个非空部分作为模块标识
                String lastPart = null;
                String secondLastPart = null;
                
                for (int i = parts.length - 1; i >= 0; i--) {
                    if (parts[i] != null && !parts[i].isEmpty()) {
                        if (lastPart == null) {
                            lastPart = parts[i];
                        } else {
                            secondLastPart = parts[i];
                            break;
                        }
                    }
                }
                
                if (secondLastPart != null) {
                    moduleKey = secondLastPart;
                    log.debug("提取到倒数第二个部分作为模块标识: {}", moduleKey);
                }
            }
            
            // 如果找到模块标识，尝试从菜单表中查询对应的 menu_name
            if (StringUtils.isNotEmpty(moduleKey)) {
                try {
                    // 使用注入的 securitySysMenuService
                    String menuName = securitySysMenuService.getMenuNameByPath(moduleKey);
                    if (StringUtils.isNotEmpty(menuName)) {
                        log.info("根据路径 {} 找到菜单名称: {}", moduleKey, menuName);
                        return menuName;
                    }
                } catch (Exception e) {
                    log.debug("查询菜单名称失败: {}", e.getMessage());
                }
            }
            
            // 如果无法从菜单表中获取，则使用 URL 中的模块名
            if (StringUtils.isNotEmpty(moduleKey)) {
                return moduleKey;
            }
            
            // 如果所有方法都失败，尝试从URI中提取一个合理的部分
            String[] parts = uri.split("/");
            if (parts.length >= 3) {
                for (int i = 2; i < parts.length; i++) {
                    if (StringUtils.isNotEmpty(parts[i]) && 
                        !parts[i].equals("import") && 
                        !parts[i].equals("upload") && 
                        !parts[i].equals("download")) {
                        return parts[i];
                    }
                }
                return parts[2];
            } else if (parts.length >= 2) {
                return parts[1];
            }
        } catch (Exception e) {
            log.error("解析模块名称失败", e);
        }
        
        return "未知模块";
    }
    
    /**
     * 获取类名的简短表示（确保不超过50个字符）
     */
    private String getShortClassName(String className) {
        if (StringUtils.isEmpty(className)) {
            return "Unknown";
        }
        
        // 如果类名已经很短，直接返回
        if (className.length() <= 50) {
            return className;
        }
        
        // 如果类名太长，截取前50个字符
        return className.substring(0, 50);
    }
    
    /**
     * 确保URL只有一个securityConm前缀，并处理重复参数
     * 
     * @param url 原始URL
     * @return 处理后的URL，确保只有一个securityConm前缀，且没有重复参数
     */
    private String ensureSecurityConmPrefix(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        
        // 先进行标准化处理（包括处理重复参数）
        String normalizedUrl = normalizeUrl(url);
        
        // 移除所有securityConm前缀，但只处理以securityConm/开头的情况
        while (normalizedUrl.startsWith("/securityConm/") || normalizedUrl.startsWith("securityConm/")) {
            if (normalizedUrl.startsWith("/securityConm/")) {
                normalizedUrl = "/" + normalizedUrl.substring("/securityConm/".length());
            } else if (normalizedUrl.startsWith("securityConm/")) {
                normalizedUrl = normalizedUrl.substring("securityConm/".length());
            }
        }
        
        // 确保URL以/开头
        if (!normalizedUrl.startsWith("/")) {
            normalizedUrl = "/" + normalizedUrl;
        }
        
        // 添加一个securityConm前缀
        return "securityConm" + normalizedUrl;
    }
}