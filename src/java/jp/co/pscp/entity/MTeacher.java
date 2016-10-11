/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.pscp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import jp.co.pscp.util.ConstUtil;

/**
 *
 * @author 雄
 */
@Entity
@Table(name = "m_teacher")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MTeacher.findAll", query = "SELECT m FROM MTeacher m"),
    @NamedQuery(name = "MTeacher.findById", query = "SELECT m FROM MTeacher m WHERE m.id = :id"),
    @NamedQuery(name = "MTeacher.findByTeaName", query = "SELECT m FROM MTeacher m WHERE m.teaName = :teaName"),
    @NamedQuery(name = "MTeacher.findBySex", query = "SELECT m FROM MTeacher m WHERE m.sex = :sex"),
    @NamedQuery(name = "MTeacher.findByBirthday", query = "SELECT m FROM MTeacher m WHERE m.birthday = :birthday"),
    @NamedQuery(name = "MTeacher.findByAddress", query = "SELECT m FROM MTeacher m WHERE m.address = :address"),
    @NamedQuery(name = "MTeacher.findByMobile", query = "SELECT m FROM MTeacher m WHERE m.mobile = :mobile"),
    @NamedQuery(name = "MTeacher.findByEmail", query = "SELECT m FROM MTeacher m WHERE m.email = :email"),
    @NamedQuery(name = "MTeacher.findByPassword", query = "SELECT m FROM MTeacher m WHERE m.password = :password"),
    @NamedQuery(name = "MTeacher.findByUpdDate", query = "SELECT m FROM MTeacher m WHERE m.updDate = :updDate"),
    @NamedQuery(name = "MTeacher.findByDelFlg", query = "SELECT m FROM MTeacher m WHERE m.delFlg = :delFlg"),
    @NamedQuery(name = "MTeacher.findByVersion", query = "SELECT m FROM MTeacher m WHERE m.version = :version")})
public class MTeacher implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Size(min = 1, max = 8)
    @Column(name = "id")
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "tea_name")
    private String teaName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sex")
    private String sex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Size(max = 200)
    @Column(name = "address")
    private String address;
    @Size(max = 15)
    @Column(name = "mobile")
    private String mobile;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="無効な電子メール")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 32)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "upd_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updDate;
    @Column(name = "del_flg")
    private String delFlg;
    @Column(name = "version")
    @Version
    private int version;

    public MTeacher() {
    }

    public MTeacher(String id) {
        this.id = id;
    }

    public MTeacher(String id, String teaName, String sex, Date birthday, String email, String password, Date updDate) {
        this.id = id;
        this.teaName = teaName;
        this.sex = sex;
        this.birthday = birthday;
        this.email = email;
        this.password = password;
        this.updDate = updDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getUpdDate() {
        return updDate;
    }

    public void setUpdDate(Date updDate) {
        this.updDate = updDate;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MTeacher)) {
            return false;
        }
        MTeacher other = (MTeacher) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jp.co.pscp.entity.MTeacher[ id=" + id + " ]";
    }
    public boolean getSexB(){
        return ConstUtil.REQUIRED_FLG_MAN.equals(this.sex);
    }
    public void setSexB(boolean flg){
        this.sex=flg ? ConstUtil.REQUIRED_FLG_MAN : ConstUtil.REQUIRED_FLG_WOMAN;
    }
}
