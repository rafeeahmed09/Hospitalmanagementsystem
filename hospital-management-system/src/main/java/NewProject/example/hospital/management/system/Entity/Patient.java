package NewProject.example.hospital.management.system.Entity;

import NewProject.example.hospital.management.system.Entity.Type.BloodType;
import NewProject.example.hospital.management.system.Entity.Type.Roles;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Patient", indexes = {
        @Index(name = "idx_patient_birth_date", columnList = "birth_date")
}, uniqueConstraints = {
        @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
        @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"first_name", "birth_date"})
})
public class Patient implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String firstName;

    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String email;
    private Boolean deleted;

    private String gender;

    @Column
    private String password;
    private boolean enable = true;

     @CreationTimestamp
     @Column(updatable = false)
     private LocalDate createDate;

     @Enumerated(EnumType.STRING)
     private BloodType bloodGroup;

    @Enumerated(EnumType.STRING)
     private List<Roles> roles = new ArrayList<>();

     @OneToMany(mappedBy = "patient",cascade = {CascadeType.ALL},orphanRemoval = true)

     @ToString.Exclude
     private List<Insurance> insurances;

     @OneToMany(mappedBy = "patient", cascade = {CascadeType.ALL},orphanRemoval = true)
     @JsonManagedReference
     private List<Appointment> appointments;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }

    @Override
    public String getUsername() {
        return this.email;
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
        return this.enable;
    }

}
