package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全会议记录对象 security_production_meeting_record
 * 
 * @author wang
 * @date 2025-03-07
 */
public class SecurityProductionMeetingRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 会议时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "会议时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date meetingTime;

    /** 会议简要内容 */
    @Excel(name = "会议简要内容")
    private String meetingContent;

    /** 会议地点 */
    @Excel(name = "会议地点")
    private String meetingLocation;

    /** 参加会议人员 */
    @Excel(name = "参加会议人员")
    private String attendees;

    /** 会议主持 */
    @Excel(name = "会议主持")
    private String meetingChairman;

    /** 会议记录人 */
    @Excel(name = "会议记录人")
    private String meetingNotes;

    /** 会议详细记录 */
    @Excel(name = "会议详细记录")
    private String meetingContent2;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMeetingTime(Date meetingTime) 
    {
        this.meetingTime = meetingTime;
    }

    public Date getMeetingTime() 
    {
        return meetingTime;
    }
    public void setMeetingContent(String meetingContent) 
    {
        this.meetingContent = meetingContent;
    }

    public String getMeetingContent() 
    {
        return meetingContent;
    }
    public void setMeetingLocation(String meetingLocation) 
    {
        this.meetingLocation = meetingLocation;
    }

    public String getMeetingLocation() 
    {
        return meetingLocation;
    }
    public void setAttendees(String attendees) 
    {
        this.attendees = attendees;
    }

    public String getAttendees() 
    {
        return attendees;
    }
    public void setMeetingChairman(String meetingChairman) 
    {
        this.meetingChairman = meetingChairman;
    }

    public String getMeetingChairman() 
    {
        return meetingChairman;
    }
    public void setMeetingNotes(String meetingNotes) 
    {
        this.meetingNotes = meetingNotes;
    }

    public String getMeetingNotes() 
    {
        return meetingNotes;
    }
    public void setMeetingContent2(String meetingContent2) 
    {
        this.meetingContent2 = meetingContent2;
    }

    public String getMeetingContent2() 
    {
        return meetingContent2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("meetingTime", getMeetingTime())
            .append("meetingContent", getMeetingContent())
            .append("meetingLocation", getMeetingLocation())
            .append("attendees", getAttendees())
            .append("meetingChairman", getMeetingChairman())
            .append("meetingNotes", getMeetingNotes())
            .append("meetingContent2", getMeetingContent2())
            .toString();
    }
}
