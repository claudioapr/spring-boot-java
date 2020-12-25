package br.claudio.dynamodb.dto;

import br.claudio.dynamodb.model.Employee;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class EmployeeDTO implements Serializable {

    private static final long serialVersionUID = 6318931228062100559L;

    @JsonProperty("company_name")
    @NotNull
    @NotBlank
    private String companyName;

    @JsonProperty("company_document_number")
    @NotNull
    @NotBlank
    private String companyDocumentNumber;

    @JsonProperty("phone_number")
    @NotNull
    @NotBlank
    private String phoneNumber;

    @JsonProperty("active")
    private Boolean active;

    public Employee employeeDTOToEmployee() {
        return new Employee(
                this.companyName,
                this.companyDocumentNumber,
                this.phoneNumber,
                true
        );
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCompanyDocumentNumber() {
        return companyDocumentNumber;
    }

    public void setCompanyDocumentNumber(String companyDocumentNumber) {
        this.companyDocumentNumber = companyDocumentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}