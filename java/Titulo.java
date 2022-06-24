package com.algaworks.cobranca.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Titulo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long codigo;
	
	@NotEmpty(message = "Descrição é obrigatória")
	@Size(max = 60, message = "A descrição não pode conter mais de 60 caracteres")
	private String descricao;
	
	@NotNull(message = "Data de vencimento é obrigatória")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	
	@Temporal(TemporalType.DATE)
	private Date dataVencimento;
	
	@NotNull(message = "Valor é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor não pode ser memor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999.,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valor;
	
	@Enumerated(EnumType.STRING)
	private StatusTitulo status;
	
	public long getCodigo() {
		return codigo;
	}
	public void setCodigo(long codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Date getDataVencimento() {
		return dataVencimento;
	}
	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	public StatusTitulo getStatus() {
		return status;
	}
	public void setStatus(StatusTitulo status) {
		this.status = status;
	}
	
	public boolean isPendente() {
		return StatusTitulo.PENDENTE.equals(this.status);		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Titulo other = (Titulo) obj;
		return codigo == other.codigo;
	}

}
