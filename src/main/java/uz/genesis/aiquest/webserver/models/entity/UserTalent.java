package uz.genesis.aiquest.webserver.models.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.genesis.aiquest.webserver.commons.annotations.SearchableField;
import uz.genesis.aiquest.webserver.models.entity.base.BaseEntityUID;
import uz.genesis.aiquest.webserver.models.enums.EGenderType;
import uz.genesis.aiquest.webserver.models.enums.UserType;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Table(name = "users", indexes = {@Index(name = "idx_user_email", columnList = "email", unique = true)})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserTalent extends BaseEntityUID implements UserDetails {

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", unique = true, updatable = false)
    @SearchableField
    private String email;
    @Column(name = "password")
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "one_id_pin", unique = true)
    private String oneIdPin;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender_type")
    private EGenderType EGenderType;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(insertable = false, updatable = false, name = "sub_direction_id")
    private SubDirection subDirection;

    @Column(name = "sub_direction_id")
    private Long subDirectionId;

    @Column(name = "region_id")
    private Long regionId;

    @CreationTimestamp
    private Timestamp lastActive;

    @Column(name = "is_profile_create_test_accepted")
    private Boolean isStandardTestPassed = Boolean.FALSE; //when the talent solve min number of tests this

    @Column(name = "is_detailed_test_accepted")
    private Boolean isDetailedTestPassed = Boolean.FALSE;


    @Column(columnDefinition = "serial", insertable = false, updatable = false, unique = true)
    protected Long displayId;

    private String emailVerificationToken;

    @Column(name = "resume_url")
    private String resumeUrl;

    @Column(name = "is_enabled")
    private Boolean isEnabled = Boolean.FALSE;

    @Column(name = "profilePhotoUrl", length = 1000)
    private String profilePhotoUrl;


    public static String _createdAtField = "createdAt";
    public static String _subdirectionId = "subDirectionId";
    public static String _isDetailedTestPassed = "isDetailedTestPassed";
    public static String _genderType = "genderType";
    public static String _isStandardTestPassed = "isStandardTestPassed";
    public static String _languages = "userLangLevels";
    public static String _yearsOfExperience = "yearsOfExperience";
    public static String _minSalary = "minSalary";

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(UserType.USER_TALENT.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return this.id.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }

}
