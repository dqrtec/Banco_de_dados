import os 
import pygame

DIR = "/home/megazzzmata/NetBeansProjects/Principal"
naoLer = ["target","pom.xml","src"]

quantidadeUnderLine = 0
ListaTabelas = ""

for arquivo in os.listdir(DIR):
	quantidadeUnderArquivo = len(arquivo.split("_"))
	if quantidadeUnderArquivo > quantidadeUnderLine:
		ListaTabelas = arquivo
		quantidadeUnderLine = quantidadeUnderArquivo
ListaTabelas = ListaTabelas[:-5].split("_")


pygame.init()

black = pygame.Color(0,0,0)
bkg = pygame.Color(255,255,255)

TelaInicial = pygame.display.set_mode((900,600))
TelaInicial.fill(bkg)

fpsController = pygame.time.Clock()
fpsController.tick(60) # 60 frames por segundo #tick(framerate=0)

pygame.display.flip()

pygame.display.set_caption('LEFT DEEP THREE')

myFont = pygame.font.SysFont('monaco', 72)

class Tabela(object):
	def __init__(self, nome,projecao,selecao,posicao):
		self.nome = nome
		self.projecao = projecao
		self.selecao = selecao
		self.posicao = posicao

		imagemSave = pygame.image.load("/home/megazzzmata/NetBeansProjects/Principal/DeepThree/tabela.png")
		imagemSave = pygame.transform.scale(imagemSave, (80, 80))
		rimagemSave = imagemSave.get_rect()
		rimagemSave = rimagemSave.move(posicao)
		self.imagemT = imagemSave
		self.retanguloT = rimagemSave

		imagemSave = pygame.image.load("/home/megazzzmata/NetBeansProjects/Principal/DeepThree/pi.png")
		imagemSave = pygame.transform.scale(imagemSave, (20, 20))
		rimagemSave = imagemSave.get_rect()
		rimagemSave = rimagemSave.move((posicao[0]+20,posicao[1]+100))
		self.imagemP = imagemSave
		self.retanguloP = rimagemSave

		imagemSave = pygame.image.load("/home/megazzzmata/NetBeansProjects/Principal/DeepThree/sigma.png")
		imagemSave = pygame.transform.scale(imagemSave, (20, 20))
		rimagemSave = imagemSave.get_rect()
		rimagemSave = rimagemSave.move(((posicao[0]+20,posicao[1]-30)))
		self.imagemS = imagemSave
		self.retanguloS = rimagemSave		

	def plotar(self,TelaInicial):
		
		TelaInicial.blit(self.imagemT, self.retanguloT)

		GOsurf = myFont.render(self.nome, True, pygame.Color(0,100,255))
		TelaInicial.blit(GOsurf,self.retanguloT)

		if self.selecao:
			TelaInicial.blit(self.imagemS, self.retanguloS)
		if self.projecao:
			TelaInicial.blit(self.imagemP, self.retanguloP)
	
	def TracarLinha(self,TelaInicial,destino):
		pygame.draw.aaline(TelaInicial,black,(self.posicao[0]+40,self.posicao[1]+80),(destino.posicao[0]+40, destino.posicao[1]))



linhas = []
tabelas = []
tuplasLinhas = [(0,3),(1,3),(2,4),(3,4),(4,5)]

tabelas.append( Tabela(ListaTabelas[0], True, True, (50,50)) )
tabelas.append( Tabela(ListaTabelas[1], True, True, (290,50)) )
tabelas.append( Tabela(ListaTabelas[2], True, True, (550,50)) )

tabelas.append( Tabela("", True, False, (170,200)) )
tabelas.append( Tabela("", True, False, (390,320)) )

tabelas.append( Tabela("JOIN", False, False, (390,500)) )


while True:
	for event in pygame.event.get():
		if event.type == pygame.QUIT:
			pygame.quit()
			sys.exit()
		if event.type == pygame.KEYDOWN:
			if event.key == pygame.K_SPACE:
				pygame.quit()
				sys.exit()

	for inicio,fim in tuplasLinhas:
		tabelas[inicio].TracarLinha(TelaInicial,tabelas[fim])
		#pygame.draw.aaline(TelaInicial,black,(i.x,i.y),(j.x,j.y))

	for tabela in tabelas:
		tabela.plotar(TelaInicial)
	
	pygame.display.flip()