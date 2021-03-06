package com.aqselmo;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.aqselmo.domain.Categoria;
import com.aqselmo.domain.Cidade;
import com.aqselmo.domain.Cliente;
import com.aqselmo.domain.Endereco;
import com.aqselmo.domain.Estado;
import com.aqselmo.domain.ItemPedido;
import com.aqselmo.domain.Pagamento;
import com.aqselmo.domain.PagamentoComBoleto;
import com.aqselmo.domain.PagamentoComCartao;
import com.aqselmo.domain.Pedido;
import com.aqselmo.domain.Produto;
import com.aqselmo.domain.enums.EstadoPagamento;
import com.aqselmo.domain.enums.TipoCliente;
import com.aqselmo.repositories.CategoriaRepository;
import com.aqselmo.repositories.CidadeRepository;
import com.aqselmo.repositories.ClienteRepository;
import com.aqselmo.repositories.EnderecoRepository;
import com.aqselmo.repositories.EstadoRepository;
import com.aqselmo.repositories.ItemPedidoRepository;
import com.aqselmo.repositories.PagamentoRepository;
import com.aqselmo.repositories.PedidoRepository;
import com.aqselmo.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Inform??tica");
		Categoria cat2 = new Categoria(null, "Escrit??rio");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		// Adicionando relacionamentos de produtos
		cat1.setProdutos(Arrays.asList(p1,p2,p3));
		cat2.setProdutos(Arrays.asList(p2));
		
		// Adicionando relacionamentos de categorias
		p1.setCategorias(Arrays.asList(cat1));
		p2.setCategorias(Arrays.asList(cat1,cat2));
		p3.setCategorias(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "S??o Paulo");
		
		Cidade c1 = new Cidade(null, "Uberl??ndia", est1);
		Cidade c2 = new Cidade(null, "S??o Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null, "Guilherme Falc??o", "guilusa25@gmail.com", "471.226.138-28", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("(11) 95383-3414"));
		
		Endereco e1 = new Endereco(null, "Rua Monte Cardoso", "275", "Nenhum", "Vila Antonieta", "03474-000", cli1, c2);
		Endereco e2 = new Endereco(null, "Rua Monte Cardoso", "277", "Nenhum", "Vila Antonieta", "03474-000", cli1, c1);
		
		cli1.setEnderecos(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/07/2017 10:32"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.setPedidos(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
	}

}
