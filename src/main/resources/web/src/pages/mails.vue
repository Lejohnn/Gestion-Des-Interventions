<template>
  <VRow>
    <VCol cols="12" sm="12" md="12">
      <VExpansionPanels variant="accordion">
        <VExpansionPanel>
          <VExpansionPanelTitle> Envoyer un Email </VExpansionPanelTitle>
          <VExpansionPanelText>
            <VForm @submit.prevent="() => {}">
              <VRow>
                <VCol cols="12" md="6">
                  <VAutocomplete v-model="demande_id" label="Demande" density="compact" persistent-placeholder
                    :items="items" />
                </VCol>

                <VCol cols="12" md="6">
                  <VTextField v-model="objet" placeholder="ex : Certificat de scolarité" label="Objet" density="compact"
                    persistent-placeholder />
                </VCol>

                <VCol cols="12" md="12">
                  <VTextarea v-model="message" placeholder="Rien à signaler." label="Message" density="compact"
                    persistent-placeholder rows="2"/>
                </VCol>

                <VCol cols="12" md="2">
                  <VBtn type="submit" block>
                    Soumettre
                  </VBtn>
                </VCol>
              </VRow>
            </VForm>
          </VExpansionPanelText>
        </VExpansionPanel>

        <VExpansionPanel>
          <VExpansionPanelTitle> Liste des Emails </VExpansionPanelTitle>
          <VExpansionPanelText>

          </VExpansionPanelText>
        </VExpansionPanel>
      </VExpansionPanels>
    </VCol>
  </VRow>
</template>

<script></script>